package de.heinrichmarkus.gradle.delphi.utils.exceptions;

public class ProjectFileNotFoundException extends RuntimeException {
    public ProjectFileNotFoundException(String message) {
        super(message);
    }
}
