package de.heinrichmarkus.gradle.delphi.utils.exceptions;

public class MsBuildFailedException extends RuntimeException {
    public MsBuildFailedException(String message) {
        super(message);
    }
}
