package llexp;

import java.util.Collections;
import java.util.List;
import java.util.Set;

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

    @Override
    public Set<Integer> getPossibleArgumentsCounts() {
        return Collections.singleton(2);
    }

    @Override
    public boolean isVarArgs() {
        return false;
    }
}
