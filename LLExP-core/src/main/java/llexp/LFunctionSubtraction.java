package llexp;

import java.util.*;

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

    private static final Set<Integer> POSSIBLE_ARGUMENTS_COUNTS;
    static {
        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        POSSIBLE_ARGUMENTS_COUNTS = Collections.unmodifiableSet(set);
    }

    @Override
    public Set<Integer> getPossibleArgumentsCounts() {
        return POSSIBLE_ARGUMENTS_COUNTS;
    }

    @Override
    public boolean isVarArgs() {
        return false;
    }
}
