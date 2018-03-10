package de.heinrichmarkus.gradle.delphi.utils.exceptions;

public class CreateZipException extends RuntimeException {
    public CreateZipException(String message) {
        super(message);
    }

    public CreateZipException(Throwable cause) {
        super(cause);
    }
}
