package de.heinrichmarkus.gradle.delphi.utils.environment;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnvVarsTest {
    @Test
    public void overwriteExistingVariable() {
        EnvVars envVars = new EnvVars();
        envVars.put("path", "one");
        envVars.put("java_home", "c:\\java home");
        envVars.put("path", "%path%; two; %java_home%");
        assertEquals("one; two; %java_home%", envVars.get("path"));
        assertEquals("one; two; c:\\java home", envVars.resolve("path"));
    }

    @Test
    public void letterCases() {
        EnvVars envVars = new EnvVars();
        envVars.put("PaTh", "one");
        assertEquals("one", envVars.resolve("path"));
        assertEquals("one", envVars.resolve("PATH"));
        assertEquals("one", envVars.resolve("pAtH"));

    }

    @Test
    public void order() {
        EnvVars envVars = new EnvVars();
        envVars.put("path", "%java_home%\\bin");
        envVars.put("java_home", "c:\\java home");
        assertEquals("c:\\java home\\bin", envVars.resolve("path"));
    }

    @Test
    public void twoVariables() {
        EnvVars envVars = new EnvVars();
        envVars.put("path", "%eins%,%zwei%");
        envVars.put("eins", "1");
        envVars.put("zwei", "2");
        assertEquals("1,2", envVars.resolve("path"));
    }

    @Test
    public void missingVar() {
        EnvVars envVars = new EnvVars();
        envVars.put("path", "%java_home%\\bin");
        assertEquals("%java_home%\\bin", envVars.get("path"));
    }
}