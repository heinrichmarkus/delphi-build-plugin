package de.heinrichmarkus.gradle.delphi.tasks;

import de.heinrichmarkus.gradle.delphi.extensions.test.TestItem;
import de.heinrichmarkus.gradle.delphi.extensions.test.TestConfiguration;
import de.heinrichmarkus.gradle.delphi.utils.LogUtils;
import de.heinrichmarkus.gradle.delphi.utils.Utils;
import de.heinrichmarkus.gradle.delphi.utils.environment.EnvVars;
import de.heinrichmarkus.gradle.delphi.utils.exceptions.TestFailedException;
import de.heinrichmarkus.gradle.delphi.utils.logger.GradleLogger;
import org.gradle.api.DefaultTask;
import org.gradle.api.logging.LogLevel;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.util.List;

public class Test extends DefaultTask {
    private TestConfiguration testConfiguration;

    @TaskAction
    public void test() {
        for (TestItem tc : testConfiguration.getItems()) {
            String command = tc.getExecutable();
            if (!tc.getArguments().trim().isEmpty()) {
                command += " " + tc.getArguments();
            }
            getLogger().lifecycle("Test " + command);
            int exitValue = Utils.exec(command, getEnvVars(), getWorkingDir(tc), new GradleLogger(getLogger(), LogLevel.LIFECYCLE));
            if (exitValue != 0) {
                throw new TestFailedException(
                        String.format("Test \"%s\" failed with exit code \"%d\"", tc.getExecutable(), exitValue));
            }
        }
    }

    private List<String> getEnvVars() {
        EnvVars envVars = new EnvVars();
        envVars.put(System.getenv());
        LogUtils.logEnvVars(getLogger(), envVars.toList());
        return envVars.toList();
    }

    private File getWorkingDir(TestItem testItem) {
        File testExe = new File(testItem.getExecutable());
        return testExe.getAbsoluteFile().getParentFile();
    }

    @Input
    public TestConfiguration getTestConfiguration() {
        return testConfiguration;
    }

    public void setTestConfiguration(TestConfiguration testConfiguration) {
        this.testConfiguration = testConfiguration;
    }
}
