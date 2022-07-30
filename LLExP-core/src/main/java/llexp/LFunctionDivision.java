package llexp;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class LFunctionDivision<E> implements LFunction<E> {
    @Override
    public Object apply(E e, List<Object> list) {
        if (list.stream().anyMatch(o -> o.getClass().equals(Double.class))) {
            double arg1 = ((Number) list.get(0)).doubleValue();
            double arg2 = ((Number) list.get(1)).doubleValue();
            return arg1 / arg2;
        } else {
            Long arg1 = ((Long) list.get(0));
            Long arg2 = ((Long) list.get(1));
            return arg1 / arg2;
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
