package llexp;

import java.util.Collections;
import java.util.List;
import java.util.Set;

class LFunctionMultiplication<E> implements LFunction<E> {
    @Override
    public Object apply(E e, List<Object> list) {
        if (list.stream().anyMatch(o -> o.getClass().equals(Double.class))) {
            double result = 1.0;
            for (Object o : list) {
                double aDouble = ((Number) o).doubleValue();
                result *= aDouble;
            }
            return result;
        } else {
            long result = 1L;
            for (Object o : list) {
                long aLong = ((Number) o).longValue();
                result *= aLong;
            }
            return result;
        }
    }

    @Override
    public Set<Integer> getPossibleArgumentsCounts() {
        return Collections.singleton(2);
    }

    @Override
    public boolean isVarArgs() {
        return true;
    }
}
