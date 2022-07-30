package llexp;

public interface EvaluatorFactory<E> {

    <R> Evaluator<E, R> createEvaluator(String exp, Class<R> resultClass);

}
