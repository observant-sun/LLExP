package llexp;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class LFunctionLogicalXor<E> implements LFunction<E> {
    @Override
    public Object apply(E e, List<Object> list) {
        return Boolean.logicalXor((Boolean) list.get(0), (Boolean) list.get(1));
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
