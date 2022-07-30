package llexp.test;

import llexp.Evaluator;
import llexp.EvaluatorFactory;
import llexp.EvaluatorFactoryProvider;

public class Main {

    public static void main(String[] args) {
        EvaluatorFactory<TestEntity> testEntityEvaluatorFactory = EvaluatorFactoryProvider.createFactory(TestEntity.class);
        Evaluator<TestEntity, Long> evaluator = testEntityEvaluatorFactory.createEvaluator("(+ a b 3)", Long.class);
        TestEntity testEntity = new TestEntity(1, 2);
        System.out.println(evaluator.eval(testEntity)); // should print 6
    }
}
