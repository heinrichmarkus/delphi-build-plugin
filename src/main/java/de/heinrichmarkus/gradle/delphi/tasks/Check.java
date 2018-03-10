package de.heinrichmarkus.gradle.delphi.tasks;

import de.heinrichmarkus.gradle.delphi.extensions.msbuild.MsbuildItem;
import de.heinrichmarkus.gradle.delphi.extensions.msbuild.MsbuildConfiguration;
import de.heinrichmarkus.gradle.delphi.extensions.test.TestConfiguration;
import de.heinrichmarkus.gradle.delphi.utils.delphi.DelphiLocator;
import de.heinrichmarkus.gradle.delphi.utils.exceptions.BinDirInvalidException;
import de.heinrichmarkus.gradle.delphi.utils.exceptions.ProjectFileNotFoundException;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.PropertyState;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

public class Check extends DefaultTask {
    private PropertyState<String> bds = getProject().property(String.class);
    private final PropertyState<String> binDirectory = getProject().property(String.class);
    private MsbuildConfiguration msbuildConfiguration;
    private TestConfiguration testConfiguration;

    @TaskAction
    public void check() {
        checkProjectFiles();
        checkBinDirectory();
        checkBdsVersion();
        getLogger().lifecycle("Configuration verified successfully");
    }

    private void checkProjectFiles() {
        for (MsbuildItem mc : msbuildConfiguration.getItems()) {
            File f = new File(mc.getFile());
            if (!f.exists()) {
                throw new ProjectFileNotFoundException(String.format("%s does not exist", f.getAbsolutePath()));
            }
        }
    }

    private void checkBinDirectory() {
        File bin = new File(binDirectory.get());
        for (MsbuildItem mc : msbuildConfiguration.getItems()) {
            File f = new File(mc.getFile());
            if (f.getAbsolutePath().contains(bin.getAbsolutePath())) {
                getLogger().error("The \"clean\" task removes the bin directory. Therefore it must not contain a project file.");
                throw new BinDirInvalidException(
                        String.format("The project file \"%s\" resides inside the bin directory \"%s\"",
                                f.getAbsolutePath(), bin.getAbsolutePath()));
            }
        }
    }

    private void checkBdsVersion() {
        DelphiLocator.locateInstallation(bds.get());
    }

    @Input
    public PropertyState<String> getBds() {
        return bds;
    }

    @Input
    public PropertyState<String> getBinDirectory() {
        return binDirectory;
    }

    @Input
    public MsbuildConfiguration getMsbuildConfiguration() {
        return msbuildConfiguration;
    }

    public void setMsbuildConfiguration(MsbuildConfiguration msbuildConfiguration) {
        this.msbuildConfiguration = msbuildConfiguration;
    }

    @Input
    public TestConfiguration getTestConfiguration() {
        return testConfiguration;
    }

    public void setTestConfiguration(TestConfiguration testConfiguration) {
        this.testConfiguration = testConfiguration;
    }
}
