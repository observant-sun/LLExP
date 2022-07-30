package llexp;

import java.util.List;
import java.util.Set;

interface LFunction<E> {

    Object apply(E e, List<Object> list);

    Set<Integer> getPossibleArgumentsCounts();

    boolean isVarArgs();

}
