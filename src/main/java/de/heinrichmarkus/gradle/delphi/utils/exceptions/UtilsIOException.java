package de.heinrichmarkus.gradle.delphi.utils.exceptions;

public class UtilsIOException extends RuntimeException {
    public UtilsIOException(Throwable cause) {
        super(cause);
    }

    public UtilsIOException(String message, Throwable cause) {
        super(message, cause);
    }
}
