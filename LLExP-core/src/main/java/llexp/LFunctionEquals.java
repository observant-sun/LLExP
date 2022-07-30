package llexp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class LFunctionEquals<E> implements LFunction<E> {
    @Override
    public Object apply(E e, List<Object> list) {
        return list.get(0).equals(list.get(1));
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
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
