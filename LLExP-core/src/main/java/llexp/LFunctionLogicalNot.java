package llexp;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class LFunctionLogicalNot<E> implements LFunction<E> {
    @Override
    public Object apply(E e, List<Object> list) {
        return !((Boolean) list.get(0));
    }

    @Override
    public Set<Integer> getPossibleArgumentsCounts() {
        return Collections.singleton(1);
    }

    @Override
    public boolean isVarArgs() {
        return false;
    }
}
