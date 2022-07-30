package llexp;

class EvaluatorImpl<E, R> implements Evaluator<E, R> {

    private final LExpression<E> lExpression;

    public EvaluatorImpl(LExpression<E> lExpression) {
        this.lExpression = lExpression;
    }

    @Override
    public R eval(E e) {
        return (R) lExpression.eval(e);
    }

}
