import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Danil Kuznetsov (kuznetsov.danil.v@gmail.com)
 */
public class EnvVariableProxyReplacer implements InvocationHandler {

    private final Object original;

    public EnvVariableProxyReplacer(Object original) {
        this.original = original;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if ("variable".equals(method.getName()) && args.length > 0) {
            String param = (String) args[0];
            String property = System.getProperty("port", "The environment variable with port is not found");
            args[0] = param.replaceAll("\\$\\{port\\}", property);
        }

        return method.invoke(original, args);
    }
}
