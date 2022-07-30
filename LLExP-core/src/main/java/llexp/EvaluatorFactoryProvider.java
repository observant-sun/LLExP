package llexp;

import java.util.HashMap;

public final class EvaluatorFactoryProvider {

    public <E> EvaluatorFactory<E> createFactory(Class<E> entityClass) {
        // TODO: 30.07.2022 Implement
        return new EvaluatorFactoryImpl<>(new HashMap<>());
    }
}
