import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Danil Kuznetsov (kuznetsov.danil.v@gmail.com)
 */
public class CustomClassLoader extends ClassLoader {

    protected CustomClassLoader() {
        super(null);
    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] b = loadClassFromDisk(normalizeFileName(name));
        return defineClass(name, b, 0, b.length);
    }

    private String normalizeFileName(String classFileName) {
        return classFileName.replaceAll("\\.", File.separator) + ".class";
    }

    private byte[] loadClassFromDisk(String fileName) {
        URL url = resolveFile(fileName);

        try (InputStream is = url.openStream()) {
            return is.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Cannot read class file");
        }
    }

    private URL resolveFile(String fileName) {

        URL file = CustomClassLoader.class.getClassLoader().getResource(fileName);

        if (file == null) {
            throw new RuntimeException("Cannot find class file");
        }

        return file;
    }
}
