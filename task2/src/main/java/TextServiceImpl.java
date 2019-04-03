/**
 * @author Danil Kuznetsov (kuznetsov.danil.v@gmail.com)
 */
public class TextServiceImpl implements TextService {

    @Override
    public String staticText() {
        return "Some static     text";
    }

    @Override
    public String variable(String variable) {
        return variable;
    }

    @Override
    public String exception(String text) throws RuntimeException {
        //TODO throw your custom exception
        return text;
    }

}
