package llexp;

import java.util.ArrayList;
import java.util.List;

class LExpression<E> {

    private final Object head;
    private final List<LExpression<E>> tail;

    LExpression(Object head, List<LExpression<E>> tail) {
        if (!(head instanceof LFunction) && tail != null) {
            // TODO: 30.07.2022 Message
            throw new IllegalArgumentException();
        }
        if (head instanceof LFunction) {
            if (tail == null) {
                // TODO: 30.07.2022 Message
                throw new IllegalArgumentException();
            }
            LFunction<E> function = (LFunction<E>) head;
            if (function.isMultipleArgument()) {
                if (function.getArgumentsType().size() != 1) {
                    // TODO: 30.07.2022 Message
                    throw new IllegalArgumentException();
                }
//                Class<?> argClass = function.getArgumentsType().get(0);
//                for (LExpression<E> lExpression : tail) {
//                    if (!argClass.isAssignableFrom(lExpression.getReturnType())) {
//                        // TODO: 30.07.2022 Message
//                        throw new IllegalArgumentException();
//                    }
//                }
            } else {
                if (function.getArgumentsType().size() != tail.size()) {
                    // TODO: 30.07.2022 Message
                    throw new IllegalArgumentException();
                }
//                for (int i = 0; i < tail.size(); i++) {
//                    // correct order.
//                    if (!tail.get(i).getReturnType().isAssignableFrom(function.getArgumentsType().get(i))) {
//                        // TODO: 30.07.2022 Message
//                        throw new IllegalArgumentException();
//                    }
//                }
            }
        }
        this.head = head;
        this.tail = tail;
    }

    Object eval(E e) {
        if (!(head instanceof LFunction)) {
            return head;
        }
        List<Object> subEvalResults = new ArrayList<>();
        for (LExpression<E> lExpression : tail) {
            Object eval = lExpression.eval(e);
            subEvalResults.add(eval);
        }
        return ((LFunction<E>) head).apply(e, subEvalResults);
    }

//    Class<?> getReturnType() {
//        if (head instanceof LFunction) {
//            return ((LFunction<E>) head).getReturnType();
//        } else {
//            return head.getClass();
//        }
//    }
}
