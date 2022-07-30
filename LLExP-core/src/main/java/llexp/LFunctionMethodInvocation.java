package llexp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
    public Set<Integer> getPossibleArgumentsCounts() {
        return Collections.singleton(0);
    }

    @Override
    public boolean isVarArgs() {
        return false;
    }
}
