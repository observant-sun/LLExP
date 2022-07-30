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
    public <R> Evaluator<E, R> createEvaluator(String exp, Class<R> resultClass) {
        if (exp == null) {
            throw new NullPointerException("expression must be non-null");
        }

        exp = exp.trim();
        if (exp.isEmpty()) {
            throw new IllegalArgumentException("expression must be non-blank");
        }

        LExpression<E> lExpression = parseString(exp);

        return new EvaluatorImpl<>(lExpression, resultClass);
    }

    private LExpression<E> parseString(String str) {
        str = str.trim();
        if (str.isEmpty()) {
            // TODO: 30.07.2022 Message, exception type
            throw new RuntimeException();
        }
        str = str.substring(1, str.length() - 1);
        String[] split = str.split(" ", 2);
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
                // TODO: 30.07.2022 Message, exception type
                throw new RuntimeException();
        }
        return new LExpression<>(lFunction, arguments);
    }

    private LExpression<E> parseArgument(String arg) {
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
