package de.heinrichmarkus.gradle.delphi.extensions;

import de.heinrichmarkus.gradle.delphi.extensions.assambly.AssemblyConfiguration;
import de.heinrichmarkus.gradle.delphi.extensions.msbuild.MsbuildConfiguration;
import de.heinrichmarkus.gradle.delphi.extensions.test.TestConfiguration;
import org.gradle.api.Project;
import org.gradle.api.provider.PropertyState;

public class DelphiBuildPluginExtension {
    final PropertyState<String> bds;
    final PropertyState<String> name;
    final PropertyState<String> bin;
    final PropertyState<String> version;
    final PropertyState<Integer> versionCode;
    final PropertyState<String> versionConstantName;
    final PropertyState<String> versionConstantFile;
    final PropertyState<String> commitConstantName;
    final PropertyState<String> commitConstantFile;
    final PropertyState<Boolean> noBrand;
    final PropertyState<Boolean> noVersionDate;
    final MsbuildConfiguration compiler;
    final TestConfiguration test;
    final AssemblyConfiguration assembly;

    public DelphiBuildPluginExtension(Project project) {
        bds = project.property(String.class);
        name = project.property(String.class);
        bin = project.property(String.class);
        version = project.property(String.class);
        versionCode = project.property(Integer.class);
        versionConstantFile = project.property(String.class);
        versionConstantName = project.property(String.class);
        commitConstantFile = project.property(String.class);
        commitConstantName = project.property(String.class);
        noBrand = project.property(Boolean.class);
        noVersionDate = project.property(Boolean.class);
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

    public PropertyState<String> getBds() {
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

    public PropertyState<String> getName() {
        return name;
    }

    public PropertyState<String> getBin() {
        return bin;
    }

    public PropertyState<String> getVersion() {
        return version;
    }

    public PropertyState<String> getVersionConstantName() {
        return versionConstantName;
    }

    public PropertyState<String> getVersionConstantFile() {
        return versionConstantFile;
    }

    public PropertyState<String> getCommitConstantName() {
        return commitConstantName;
    }

    public PropertyState<String> getCommitConstantFile() {
        return commitConstantFile;
    }

    public PropertyState<Boolean> getNoBrand() {
        return noBrand;
    }

    public PropertyState<Boolean> getNoVersionDate() {
        return noVersionDate;
    }

    public PropertyState<Integer> getVersionCode() {
        return versionCode;
    }
}
