package llexp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class LFunctionEquals<E> implements LFunction<E> {
    @Override
    public Object apply(E e, List<Object> list) {
        Object arg1 = list.get(0);
        Object arg2 = list.get(1);
        if (arg1 instanceof Number && arg2 instanceof Number) {
            if (arg1 instanceof Double || arg2 instanceof Double) {
                return ((Number) arg1).doubleValue() == ((Number) arg2).doubleValue();
            } else {
                return ((Number) arg1).longValue() == ((Number) arg2).longValue();
            }
        } else {
            return arg1.equals(arg2);
        }
    }

    private static List<Class<?>> ARGUMENTS_TYPES = new ArrayList<>();
    static {
        ARGUMENTS_TYPES.add(Object.class);
        ARGUMENTS_TYPES.add(Object.class);
        ARGUMENTS_TYPES = Collections.unmodifiableList(ARGUMENTS_TYPES);
    }
    @Override
    public List<Class<?>> getArgumentsType() {
        return ARGUMENTS_TYPES;
    }

    @Override
    public boolean isMultipleArgument() {
        return false;
    }
}
