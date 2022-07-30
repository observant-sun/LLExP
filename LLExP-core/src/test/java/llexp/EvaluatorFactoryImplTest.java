package llexp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EvaluatorFactoryImplTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest
    @MethodSource("createEvaluatorLongArgumentsProvider")
    void createEvaluator_simpleLong(String expr, Object result) {
        EvaluatorFactory<Object> evaluatorFactory = new EvaluatorFactoryImpl<>(Collections.emptyMap());
        Evaluator<Object, Long> evaluator = evaluatorFactory.createEvaluator(expr, Long.class);
        Long eval = evaluator.eval(new Object());
        assertEquals(result, eval);
    }

    private static Stream<Arguments> createEvaluatorLongArgumentsProvider() {
        return Stream.of(
                Arguments.arguments("(+ 1 2)", 3L),
                Arguments.arguments("(+ 12 32)", 12L + 32L),
                Arguments.arguments("(+ 32)", 32L),
                Arguments.arguments("(+ 10   100 10)", 120L),
                Arguments.arguments("(+ (+ 1 2) 3)", 6L),
                Arguments.arguments("(+ (+ 1 2) (+ 1 2) (+ (+ 1 2) 2))", 11L),
                Arguments.arguments("(+   (+ 1 2)    (+ 1 2) (+ (+ 1) 2))", 9L),
                Arguments.arguments("(- 10 1)", 9L),
                Arguments.arguments("(- -10 (+ 1 (- 1 3)))", -9L),
                Arguments.arguments("(if (= 1 1) 1 2)", 1L),
                Arguments.arguments("(if (= (/ 5 4) 1) 1 0)", 1L),
                Arguments.arguments("(if (= (/ 5.0 4) 1) 1 0)", 0L),
                Arguments.arguments("(* 10 9)", 90L)
                );
    }

    private static class TestClass {
        private final int a;

        private TestClass(int a) {
            this.a = a;
        }

        public int getA() {
            return a;
        }
    }

    @Test
    void createEvaluator_methodInvocation() throws NoSuchMethodException {
        String expr = "(if (= a 1.0) 1 0)";
        Map<String, Method> nameMethodMap = new HashMap<>();
        nameMethodMap.put("a", TestClass.class.getMethod("getA", (Class<?>[]) null));

        EvaluatorFactory<TestClass> evaluatorFactory = new EvaluatorFactoryImpl<>(nameMethodMap);
        Evaluator<TestClass, Long> evaluator = evaluatorFactory.createEvaluator(expr, Long.class);


        TestClass t1 = new TestClass(1);
        Long eval = evaluator.eval(t1);
        assertEquals(1, eval);

        TestClass t2 = new TestClass(2);
        eval = evaluator.eval(t2);
        assertEquals(0, eval);
    }

    @ParameterizedTest
    @MethodSource("createEvaluatorDoubleArgumentsProvider")
    void createEvaluator_simpleDouble(String expr, Object result) {
        EvaluatorFactory<Object> evaluatorFactory = new EvaluatorFactoryImpl<>(Collections.emptyMap());
        Evaluator<Object, Double> evaluator = evaluatorFactory.createEvaluator(expr, Double.class);
        Double eval = evaluator.eval(new Object());
        assertEquals((Double) result, eval, 1e-7);
    }

    private static Stream<Arguments> createEvaluatorDoubleArgumentsProvider() {
        return Stream.of(
                Arguments.arguments("(+ 1.0 2.0)", 3.0),
                Arguments.arguments("(+ 12.1 32.1)", 12.1 + 32.1),
                Arguments.arguments("(+ 32.0)", 32.0),
                Arguments.arguments("(+ 10   100 10)", 120.0),
                Arguments.arguments("(+   (+ 1 2)    (+ 1 2) (+ (+ 1) 2))", 9.0),
                Arguments.arguments("(- 10 1)", 9.0),
                Arguments.arguments("(- -10 (+ 1.0 (- 1. 3.)))", -9.0),
                Arguments.arguments("(if (= .2 .2) 1 2.0)", 1.0),
                Arguments.arguments("(if (= (/ 5.0 4) 1) 1 0)", 0.0),
                Arguments.arguments("(/ 10. 9)", 10.0 / 9)
        );
    }


}
