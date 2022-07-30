package llexp;

import java.util.Collections;
import java.util.List;
import java.util.Set;

class LFunctionAddition<E> implements LFunction<E> {

    @Override
    public Object apply(E e, List<Object> list) {
        if (list.stream().anyMatch(o -> o.getClass().equals(Double.class))) {
            double result = 0.0;
            for (Object o : list) {
                double aDouble = ((Number) o).doubleValue();
                result += aDouble;
            }
            return result;
        } else {
            long result = 0L;
            for (Object o : list) {
                long aLong = ((Number) o).longValue();
                result += aLong;
            }
            return result;
        }
    }

    @Override
    public Set<Integer> getPossibleArgumentsCounts() {
        return Collections.singleton(1);
    }

    @Override
    public boolean isVarArgs() {
        return true;
    }
}
