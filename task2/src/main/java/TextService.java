/**
 * @author Danil Kuznetsov (kuznetsov.danil.v@gmail.com)
 */
public interface TextService {
    String staticText();

    String variable(String variable);

    String exception(String text) throws RuntimeException;
}
