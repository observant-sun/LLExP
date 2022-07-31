package llexp;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public final class EvaluatorFactoryProvider {

    public static <E> EvaluatorFactory<E> createFactory(Class<E> entityClass) throws MethodNotFoundException {
        String resourceName = entityClass.getSimpleName() + ".llexp.json";
        Map<String, Method> stringMethodMap = new HashMap<>();
        try (InputStream is = entityClass.getResourceAsStream(resourceName)) {
            if (is == null) {
                throw new RuntimeException("resource " + resourceName + " not found");
            }
            String text = new Scanner(is, "UTF-8").useDelimiter("\\A").next();
            JSONObject object = new JSONObject(text);
            Map<String, Object> stringObjectMap = object.toMap();
            stringObjectMap.forEach((s, o) -> {
                String o1 = (String) o;
                try {
                    Method method = entityClass.getMethod(o1, (Class<?>[]) null);
                    stringMethodMap.put(s, method);
                } catch (NoSuchMethodException e) {
                    throw new MethodNotFoundException("There is no method " + e.getMessage() + " (see key \"" + s + "\")");
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new EvaluatorFactoryImpl<>(stringMethodMap);
    }

    public static <E> EvaluatorFactory<E> createFactory(Class<E> entityClass, Map<String, String> methodMap) {
        Map<String, Method> stringMethodMap = new HashMap<>();
        methodMap.forEach((key, methodName) -> {
            try {
                Method method = entityClass.getMethod(methodName, (Class<?>[]) null);
                stringMethodMap.put(key, method);
            } catch (NoSuchMethodException e) {
                throw new MethodNotFoundException("There is no method " + e.getMessage() + " (see key \"" + key + "\")");
            }
        });
        return new EvaluatorFactoryImpl<>(stringMethodMap);
    }
}
