package de.heinrichmarkus.gradle.delphi.utils.environment;

class EnvironmentVariableNotFound extends RuntimeException {
    public EnvironmentVariableNotFound(String message) {
        super(message);
    }
}
