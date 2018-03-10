package de.heinrichmarkus.gradle.delphi.extensions.test;

public class TestItem {
    private String executable;
    private String arguments;

    public TestItem(String executable) {
        this.executable = executable;
    }

    public TestItem(String executable, String arguments) {
        this.executable = executable;
        this.arguments = arguments;
    }

    public String getExecutable() {
        return executable;
    }

    public void setExecutable(String executable) {
        this.executable = executable;
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }
}
