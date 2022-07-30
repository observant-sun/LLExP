package llexp;

import java.util.List;

interface LFunction<E> {

    Object apply(E e, List<Object> list);

    Class<?> getReturnType();

    List<Class<?>> getArgumentsType();

    boolean isMultipleArgument();

}
