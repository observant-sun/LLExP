package llexp;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public final class EvaluatorFactoryProvider {

    public static <E> EvaluatorFactory<E> createFactory(Class<E> entityClass) {
        String resourceName = entityClass.getSimpleName() + ".llexp.json";
        Map<String, Method> stringMethodMap = new HashMap<>();
        try (InputStream is = entityClass.getResourceAsStream(resourceName)) {
            if (is == null) {
                // TODO: 30.07.2022 Message, exception type
                throw new RuntimeException();
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
                    // TODO: 30.07.2022 Message, exception type
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new EvaluatorFactoryImpl<>(stringMethodMap);
    }
}
