package llexp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LFunctionSubtraction<E> implements LFunction<E> {
    @Override
    public Object apply(E e, List<Object> list) {
        if (list.stream().anyMatch(o -> o.getClass().equals(Double.class))) {
            double arg1 = ((Number) list.get(0)).doubleValue();
            double arg2 = ((Number) list.get(1)).doubleValue();
            return arg1 - arg2;
        } else {
            Long arg1 = ((Long) list.get(0));
            Long arg2 = ((Long) list.get(1));
            return arg1 - arg2;
        }
    }

    private static List<Class<?>> ARGUMENTS_TYPES = new ArrayList<>();
    static {
        ARGUMENTS_TYPES.add(Number.class);
        ARGUMENTS_TYPES.add(Number.class);
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
