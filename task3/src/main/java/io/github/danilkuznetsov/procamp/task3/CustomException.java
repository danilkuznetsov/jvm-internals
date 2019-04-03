package io.github.danilkuznetsov.procamp.task3;

/**
 * @author Danil Kuznetsov (kuznetsov.danil.v@gmail.com)
 */
public class CustomException extends RuntimeException {
    protected CustomException(String message, boolean enableStackTrace) {
        super(message, null, enableStackTrace, enableStackTrace);
    }
}
