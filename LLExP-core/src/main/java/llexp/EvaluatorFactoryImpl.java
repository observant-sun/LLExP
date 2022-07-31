package llexp;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class EvaluatorFactoryImpl<E> implements EvaluatorFactory<E> {

    private final Map<String, Method> nameMethodMap;

    EvaluatorFactoryImpl(Map<String, Method> nameMethodMap) {
        this.nameMethodMap = nameMethodMap;
    }

    @Override
    public <R> Evaluator<E, R> createEvaluator(String expr, Class<R> resultClass) {
        if (expr == null) {
            throw new NullPointerException("expression must be non-null");
        }
        if (resultClass == null) {
            throw new NullPointerException("result class must be non-null");
        }

        expr = expr.trim();
        if (expr.isEmpty()) {
            throw new IllegalArgumentException("expression must be non-blank");
        }

        LExpression<E> lExpression = parseString(expr);

        return new EvaluatorImpl<>(lExpression, resultClass);
    }

    private LExpression<E> parseString(String str) {
        str = str.trim();
        if (str.isEmpty()) {
            throw new RuntimeException("unexpected empty string (library problem, contact developer)");
        }
        if (str.charAt(0) != '(') {
            throw new IllegalArgumentException("'(' expected");
        }
        if (str.charAt(str.length() - 1) != ')') {
            throw new IllegalArgumentException("No matching parentheses for " + str.split(" ", 2)[0]);
        }
        str = str.substring(1, str.length() - 1);
        String[] split = str.split(" ", 2);
        if (split.length == 1) {
            throw new UnsupportedOperationException("Functions with no arguments are unsupported (see \"(" + split[0] + ")\")");
        }
        String headStr = split[0];
        String argsStr = split[1];
        List<LExpression<E>> arguments = new ArrayList<>();
        while (!argsStr.isEmpty()) {
            argsStr = argsStr.trim();
            if (argsStr.startsWith("(")) {
                int index = 0;
                int level = 1;
                while (level != 0) {
                    index++;
                    if (index >= argsStr.length()) {
                        throw new IllegalArgumentException("No matching parentheses for \"" + argsStr.split(" ", 2)[0] + "\"");
                    }
                    if (argsStr.charAt(index) == '(') {
                        level++;
                    } else if (argsStr.charAt(index) == ')') {
                        level--;
                    }
                }
                arguments.add(parseString(argsStr.substring(0, index + 1)));
                argsStr = argsStr.substring(index + 1);
            } else {
                String[] argSplit = argsStr.split(" ", 2);
                arguments.add(parseArgument(argSplit[0]));
                argsStr = argSplit.length == 2 ? argSplit[1] : "";
            }
        }
        LFunction<E> lFunction;
        switch (headStr) {
            case "+":
                lFunction = new LFunctionAddition<>();
                break;
            case "-":
                lFunction = new LFunctionSubtraction<>();
                break;
            case "*":
                lFunction = new LFunctionMultiplication<>();
                break;
            case "/":
                lFunction = new LFunctionDivision<>();
                break;
            case "if":
                lFunction = new LFunctionConditionalBranching<>();
                break;
            case "=":
                lFunction = new LFunctionEquals<>();
                break;
            default:
                throw new UnsupportedOperationException("function \"" + headStr + "\" is undefined");
        }
        return new LExpression<>(lFunction, arguments);
    }

    private LExpression<E> parseArgument(String arg) {
        if (arg.endsWith(")") || arg.endsWith("(")) {
            throw new IllegalArgumentException("Non-matching closing parentheses");
        }
        if (nameMethodMap.containsKey(arg)) {
            return new LExpression<>(new LFunctionMethodInvocation<>(nameMethodMap.get(arg)), Collections.emptyList());
        }

        try {
            return new LExpression<>(Long.parseLong(arg), null);
        } catch (Exception ignored) {}

        try {
            return new LExpression<>(Double.parseDouble(arg), null);
        } catch (Exception ignored) {}

        return new LExpression<>(arg, null);

    }
}
