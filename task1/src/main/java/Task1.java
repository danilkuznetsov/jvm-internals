import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Danil Kuznetsov (kuznetsov.danil.v@gmail.com)
 */
public class Task1 {

    public static void main(String[] args) {
        while (true) {
            Class<?> clazz = reloadClassFile("TextService");
            System.out.println((String) createInstanceAndInvokeMethod(clazz, "staticText"));
            sleep(3000);
        }
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Object createInstanceAndInvokeMethod(Class<?> clazz, String methodName) {
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();
            Method staticTextMethod = clazz.getDeclaredMethod(methodName);
            return staticTextMethod.invoke(instance);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Cannot create instance and run selected method");
        }
    }

    private static Class<?> reloadClassFile(String classFileName) {
        try {
            ClassLoader cl = new CustomClassLoader();
            return cl.loadClass(classFileName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load class file");
        }
    }
}
