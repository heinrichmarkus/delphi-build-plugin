package de.heinrichmarkus.gradle.delphi.extensions;

import de.heinrichmarkus.gradle.delphi.extensions.assambly.AssemblyConfiguration;
import de.heinrichmarkus.gradle.delphi.extensions.msbuild.MsbuildConfiguration;
import de.heinrichmarkus.gradle.delphi.extensions.test.TestConfiguration;
import org.gradle.api.Project;
import org.gradle.api.provider.Property;

public class DelphiBuildPluginExtension {
    private final Property<String> bds;
    private final Property<String> name;
    private final Property<String> bin;
    private final Property<String> version;
    private final Property<Integer> versionCode;
    private final Property<String> versionConstantName;
    private final Property<String> versionConstantFile;
    private final Property<String> commitConstantName;
    private final Property<String> commitConstantFile;
    private final Property<Boolean> noBrand;
    private final Property<Boolean> noVersionDate;
    private final MsbuildConfiguration compiler;
    private final TestConfiguration test;
    private final AssemblyConfiguration assembly;

    public DelphiBuildPluginExtension(Project project) {
        bds = project.getObjects().property(String.class);
        name = project.getObjects().property(String.class);
        bin = project.getObjects().property(String.class);
        version = project.getObjects().property(String.class);
        versionCode = project.getObjects().property(Integer.class);
        versionConstantFile = project.getObjects().property(String.class);
        versionConstantName = project.getObjects().property(String.class);
        commitConstantFile = project.getObjects().property(String.class);
        commitConstantName = project.getObjects().property(String.class);
        noBrand = project.getObjects().property(Boolean.class);
        noVersionDate = project.getObjects().property(Boolean.class);
        compiler = new MsbuildConfiguration();
        test = new TestConfiguration();
        assembly = new AssemblyConfiguration(project);

        bin.set("bin/");
        versionCode.set(0);
        versionConstantFile.set("");
        versionConstantName.set("");
        commitConstantFile.set("");
        commitConstantName.set("");
        noBrand.set(false);
        noVersionDate.set(false);
        assembly.getName().set(name);
    }

    public Property<String> getBds() {
        return bds;
    }

    public MsbuildConfiguration getCompiler() {
        return compiler;
    }

    public TestConfiguration getTest() {
        return test;
    }

    public AssemblyConfiguration getAssembly() {
        return assembly;
    }

    public Property<String> getName() {
        return name;
    }

    public Property<String> getBin() {
        return bin;
    }

    public Property<String> getVersion() {
        return version;
    }

    public Property<String> getVersionConstantName() {
        return versionConstantName;
    }

    public Property<String> getVersionConstantFile() {
        return versionConstantFile;
    }

    public Property<String> getCommitConstantName() {
        return commitConstantName;
    }

    public Property<String> getCommitConstantFile() {
        return commitConstantFile;
    }

    public Property<Boolean> getNoBrand() {
        return noBrand;
    }

    public Property<Boolean> getNoVersionDate() {
        return noVersionDate;
    }

    public Property<Integer> getVersionCode() {
        return versionCode;
    }
}
