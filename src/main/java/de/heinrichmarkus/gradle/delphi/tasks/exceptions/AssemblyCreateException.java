package de.heinrichmarkus.gradle.delphi.tasks.exceptions;

public class AssemblyCreateException extends RuntimeException {
    public AssemblyCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
