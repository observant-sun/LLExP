package llexp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class LFunctionConditionalBranching<E> implements LFunction<E> {
    @Override
    public Object apply(E e, List<Object> list) {
        Boolean condition = (Boolean) list.get(0);
        if (condition) {
            return list.get(1);
        } else {
            return list.get(2);
        }
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }

    private static List<Class<?>> ARGUMENTS_TYPES = new ArrayList<>();
    static {
        ARGUMENTS_TYPES.add(Boolean.class);
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
