package llexp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

class LFunctionMethodInvocation<E> implements LFunction<E> {

    private final Method method;

    LFunctionMethodInvocation(Method method) {
        this.method = method;
    }

    @Override
    public Object apply(E e, List<Object> list) {
        try {
            return method.invoke(e);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Class<?> getReturnType() {
        return Method.class;
    }

    @Override
    public List<Class<?>> getArgumentsType() {
        return Collections.emptyList();
    }

    @Override
    public boolean isMultipleArgument() {
        return false;
    }
}
