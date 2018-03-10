package de.heinrichmarkus.gradle.delphi.utils.environment;

public class EnvironmentVariableNotFound extends RuntimeException {
    public EnvironmentVariableNotFound(String message) {
        super(message);
    }
}
