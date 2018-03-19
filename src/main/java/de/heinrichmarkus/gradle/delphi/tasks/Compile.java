package de.heinrichmarkus.gradle.delphi.tasks;

import de.heinrichmarkus.gradle.delphi.extensions.msbuild.MsbuildItem;
import de.heinrichmarkus.gradle.delphi.extensions.msbuild.MsbuildConfiguration;
import de.heinrichmarkus.gradle.delphi.utils.LogUtils;
import de.heinrichmarkus.gradle.delphi.utils.delphi.DProjFile;
import de.heinrichmarkus.gradle.delphi.utils.delphi.DelphiLocator;
import de.heinrichmarkus.gradle.delphi.utils.environment.EnvVars;
import de.heinrichmarkus.gradle.delphi.utils.environment.RsVarsReader;
import de.heinrichmarkus.gradle.delphi.utils.Utils;
import de.heinrichmarkus.gradle.delphi.utils.exceptions.MsBuildFailedException;
import de.heinrichmarkus.gradle.delphi.utils.exceptions.RsVarsNotFoundException;
import de.heinrichmarkus.gradle.delphi.utils.logger.FileLogger;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.PropertyState;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.util.List;

public class Compile extends DefaultTask {
    private Property<String> bin = getProject().getObjects().property(String.class);
    private Property<String> bds = getProject().getObjects().property(String.class);
    private MsbuildConfiguration msbuildConfiguration;

    @TaskAction
    public void compile() {
        if (!msbuildConfiguration.getItems().isEmpty()) {
            getLogger().lifecycle("Compiling with BDS " + bds.get());
            for (MsbuildItem p : msbuildConfiguration.getItems()) {
                getLogger().lifecycle(String.format("\t- %s", p.toString()));
                compileProject(p);
            }
        } else {
            getLogger().warn("Nothing to compile! Add project files to project.dproj property.");
        }
    }

    private void compileProject(MsbuildItem msbuildConfig) {
        RsVarsReader reader = new RsVarsReader(getRsvarsLocation());
        String cmdMsBuild = makeBuildCommand(msbuildConfig, reader);
        EnvVars envVars = composeEnvVars(reader);
        getLogger().info(String.format("\t\t- %s", cmdMsBuild));
        selectConfigAndPlatform(msbuildConfig);
        execCommand(cmdMsBuild, envVars.toList());
    }

    private void selectConfigAndPlatform(MsbuildItem msbuildConfig) {
        File file = new File(msbuildConfig.getFile());
        DProjFile dProjFile = new DProjFile(file);
        dProjFile.setConfigAndPlatform(msbuildConfig.getConfig(), msbuildConfig.getPlatform());
    }

    private EnvVars composeEnvVars(RsVarsReader reader) {
        EnvVars envVars = new EnvVars();
        envVars.put(System.getenv());
        envVars.put(reader.toMap());
        return envVars;
    }

    private String makeBuildCommand(MsbuildItem config, RsVarsReader reader) {
        String frameworkDir = reader.read("FrameworkDir");
        File msbuild = new File(frameworkDir, "msbuild.exe");
        String command =  String.format("%s %s /t:Build /p:config=%s /p:platform=%s", msbuild.getAbsolutePath(),
                config.getFile(), config.getConfig(), config.getPlatform());
        if (config.getTarget() != null && !config.getTarget().isEmpty()) {
            command += " /target:" + config.getTarget();
        }
        return command;
    }

    private void execCommand(String command, List<String> envVars) {
        LogUtils.logEnvVars(getLogger(), envVars);
        File logFile = new File(bin.get(), "log/compile.log");
        FileLogger fileLogger = new FileLogger(logFile);
        int exitValue = Utils.exec(command, envVars, null, fileLogger);
        fileLogger.flush();
        if (exitValue != 0) {
            throw new MsBuildFailedException(
                    String.format("msbuild finished with exit code '%d'. See '%s' for more details.", exitValue, logFile.getAbsolutePath()));
        }
    }

    private File getRsvarsLocation() {
        File delphiLocation = DelphiLocator.locateInstallation(bds.get());
        File rsvars = new File(delphiLocation, "bin/rsvars.bat");
        if (!rsvars.exists()) {
            throw new RsVarsNotFoundException(rsvars.getAbsolutePath());
        }
        return rsvars;
    }

    @Input
    public Property<String> getBin() {
        return bin;
    }

    @Input
    public Property<String> getBds() {
        return bds;
    }

    @Input
    public MsbuildConfiguration getMsbuildConfiguration() {
        return msbuildConfiguration;
    }

    public void setMsbuildConfiguration(MsbuildConfiguration dproj) {
        this.msbuildConfiguration = dproj;
    }
}
