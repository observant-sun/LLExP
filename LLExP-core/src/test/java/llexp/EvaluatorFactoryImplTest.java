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
                Arguments.arguments("(if (= 1 1) 1 2)", 1L)
                );
    }

    private static class TestClass {
        private int a;

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }
    }

    @Test
    void createEvaluator_methodInvocation() throws NoSuchMethodException {
        String expr = "(if (= a 1) 1 2)";
        TestClass t = new TestClass();
        t.setA(1);
        Map<String, Method> nameMethodMap = new HashMap<>();
        nameMethodMap.put("a", TestClass.class.getMethod("getA", (Class<?>[]) null));

        EvaluatorFactory<TestClass> evaluatorFactory = new EvaluatorFactoryImpl<>(nameMethodMap);
        Evaluator<TestClass, Long> evaluator = evaluatorFactory.createEvaluator(expr, Long.class);
        Long eval = evaluator.eval(t);

        assertEquals(2, eval);
    }


}
