package llexp;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class LFunctionGreaterThan<E> implements LFunction<E> {

    @Override
    public Object apply(E o, List<Object> list) {
        if (list.get(0) instanceof Double || list.get(1) instanceof Double) {
            return ((Number) list.get(0)).doubleValue() > ((Number) list.get(1)).doubleValue();
        } else {
            return ((Number) list.get(0)).longValue() > ((Number) list.get(1)).longValue();
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
