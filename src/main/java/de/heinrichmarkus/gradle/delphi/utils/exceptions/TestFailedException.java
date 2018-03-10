package de.heinrichmarkus.gradle.delphi.utils.exceptions;

public class TestFailedException extends RuntimeException {
    public TestFailedException(String message) {
        super(message);
    }
}
