package llexp;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class LFunctionLogicalAnd<E> implements LFunction<E> {
    @Override
    public Object apply(E e, List<Object> list) {
        return list.stream()
                .map(o -> (Boolean) o)
                .reduce(true, (o1, o2) -> o1 && o2);
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
