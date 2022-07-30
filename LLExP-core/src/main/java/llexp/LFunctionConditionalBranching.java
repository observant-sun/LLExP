package llexp;

import java.util.Collections;
import java.util.List;
import java.util.Set;

class LFunctionConditionalBranching<E> implements LFunction<E> {
    @Override
    public Object apply(E e, List<Object> list) {
        Boolean condition = (Boolean) list.get(0);
        if (condition) {
            return list.get(1);
        } else {
            return list.get(2);
        }
    }

    @Override
    public Set<Integer> getPossibleArgumentsCounts() {
        return Collections.singleton(3);
    }

    @Override
    public boolean isVarArgs() {
        return false;
    }
}
