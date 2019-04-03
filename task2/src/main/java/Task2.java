import java.lang.reflect.Proxy;

/**
 * @author Danil Kuznetsov (kuznetsov.danil.v@gmail.com)
 */
public class Task2 {
    public static void main(String[] args) {

        TextService originalService = new TextServiceImpl();

        TextService proxy = (TextService) Proxy.newProxyInstance(
                originalService.getClass().getClassLoader(),
                new Class<?>[]{TextService.class},
                new EnvVariableProxyReplacer(originalService)
        );

        System.out.println(proxy.variable("server.port = ${port}"));
    }
}
