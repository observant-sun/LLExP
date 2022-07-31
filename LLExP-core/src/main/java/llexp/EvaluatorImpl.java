package llexp;

class EvaluatorImpl<E, R> implements Evaluator<E, R> {

    private final LExpression<E> lExpression;
    private final Class<R> resultClass;

    EvaluatorImpl(LExpression<E> lExpression, Class<R> resultClass) {
        this.lExpression = lExpression;
        this.resultClass = resultClass;
    }

    @SuppressWarnings("unchecked")
    @Override
    public R eval(E e) {
        Object result = lExpression.eval(e);
        if (resultClass.isInstance(result)) {
            return (R) result;
        }
        if (resultClass.equals(String.class)) {
            return (R) result.toString();
        }
        if (resultClass.equals(Long.class)) {
            if (result instanceof Double) {
                return (R) (Long) ((Double) result).longValue();
            }
            if (result instanceof String) {
                return (R) Long.valueOf((String) result);
            }
        }
        if (resultClass.equals(Double.class)) {
            if (result instanceof Long) {
                return (R) (Double) ((Long) result).doubleValue();
            }
            if (result instanceof String) {
                return (R) Double.valueOf((String) result);
            }
        }
        throw new RuntimeException("there is no way to get " + resultClass.getName() + " from " + result.getClass());
    }

}
