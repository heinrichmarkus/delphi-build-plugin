package de.heinrichmarkus.gradle.delphi;

import de.heinrichmarkus.gradle.delphi.extensions.DelphiBuildPluginExtension;
import de.heinrichmarkus.gradle.delphi.tasks.*;
import de.heinrichmarkus.gradle.delphi.utils.ProjectDir;
import org.gradle.api.DefaultTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class DelphiBuildPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        ProjectDir.getInstance().setDir(project.getProjectDir());
        DelphiBuildPluginExtension extension = project.getExtensions().create("project", DelphiBuildPluginExtension.class, project);
        project.getTasks().create("readVersion", ReadConstantTask.class, task -> configureReadVersion(task, extension));
        project.getTasks().create("writeVersionConstant", WriteVersionConstantTask.class, task -> configureWriteVersionConstant(task, extension));
        project.getTasks().create("writeProjectVersion", WriteProjectVersionTask.class, task -> configureWriteProjectVersion(task, extension));
        project.getTasks().create("readCommit", ReadConstantTask.class, task -> configureReadCommit(task, extension));
        project.getTasks().create("writeCommit", WriteCommitTask.class, task -> configureWriteCommit(task, extension));
        project.getTasks().create("listCompilers", ListCompilers.class, task -> configureListCompilers(task));
        project.getTasks().create("check", Check.class, task -> configureCheckTask(task, extension));
        project.getTasks().create("clean", Clean.class, task -> configureClean(task, extension));
        project.getTasks().create("brand", Brand.class, task -> configureBrandTask(task));
        project.getTasks().create("compile", Compile.class, task -> configureCompileTask(task, extension));
        project.getTasks().create("test", Test.class, task -> configureTestTask(task, extension));
        project.getTasks().create("assemble", Assemble.class, task -> configureAssembleTask(task, extension));
        project.getTasks().create("build", Build.class, task -> configureBuildTask(task));
    }

    private void configureClean(Clean task, DelphiBuildPluginExtension extension) {
        setGroup(task);
        task.setDescription("Delete binary files");
        task.dependsOn("check");
        task.getBinDirectory().set(extension.getBin());
    }

    private void configureReadVersion(ReadConstantTask task, DelphiBuildPluginExtension extension) {
        setGroupInfo(task);
        task.setDescription("Display version from source-file");
        task.getFileName().set(extension.getVersionConstantFile());
        task.getConstantName().set(extension.getVersionConstantName());
    }

    private void configureWriteVersionConstant(WriteVersionConstantTask task, DelphiBuildPluginExtension extension) {
        setGroupHelp(task);
        task.setDescription("Write version to a constant in source code");
        task.getFileName().set(extension.getVersionConstantFile());
        task.getConstantName().set(extension.getVersionConstantName());
        task.getVersion().set(extension.getVersion());
        task.getDisabled().set(extension.getNoBrand());
        task.getNoDate().set(extension.getNoVersionDate());
    }

    private void configureWriteProjectVersion(WriteProjectVersionTask task, DelphiBuildPluginExtension extension) {
        setGroupHelp(task);
        task.setDescription("Write version to project files");
        task.getVersion().set(extension.getVersion());
        task.getVersionCode().set(extension.getVersionCode());
        task.setMsbuildConfiguration(extension.getCompiler());
        task.getDisabled().set(extension.getNoBrand());
    }

    private void configureReadCommit(ReadConstantTask task, DelphiBuildPluginExtension extension) {
        setGroupInfo(task);
        task.setDescription("Display commit id from source-file");
        task.getFileName().set(extension.getCommitConstantFile());
        task.getConstantName().set(extension.getCommitConstantName());
    }

    private void configureWriteCommit(WriteCommitTask task, DelphiBuildPluginExtension extension) {
        setGroupHelp(task);
        task.setDescription("Write commit id to project files");
        task.getFileName().set(extension.getCommitConstantFile());
        task.getConstantName().set(extension.getCommitConstantName());
        task.getDisabled().set(extension.getNoBrand());
    }

    private void configureListCompilers(ListCompilers task) {
        setGroupInfo(task);
        task.setDescription("Display list of Delphi installations");
    }

    private void configureCheckTask(Check task, DelphiBuildPluginExtension extension) {
        setGroupInfo(task);
        task.setDescription("Check project configuration");
        task.getBds().set(extension.getBds());
        task.getBinDirectory().set(extension.getBin());
        task.setMsbuildConfiguration(extension.getCompiler());
        task.setTestConfiguration(extension.getTest());
    }

    private void configureBrandTask(Brand task) {
        setGroup(task);
        task.setDescription("Brand version and commit to source files");
        task.dependsOn("check", "writeVersionConstant", "writeProjectVersion", "writeCommit");
    }

    private void configureCompileTask(Compile task, DelphiBuildPluginExtension extension) {
        setGroup(task);
        task.dependsOn("brand");
        task.getBin().set(extension.getBin());
        task.getBds().set(extension.getBds());
        task.setMsbuildConfiguration(extension.getCompiler());
    }

    private void configureTestTask(Test task, DelphiBuildPluginExtension extension) {
        setGroup(task);
        task.dependsOn("compile");
        task.setTestConfiguration(extension.getTest());
    }

    private void configureAssembleTask(Assemble task, DelphiBuildPluginExtension extension) {
        setGroup(task);
        task.dependsOn("compile");
        task.getBin().set(extension.getBin());
        task.getVersion().set(extension.getVersion());
        task.setAssemblyConfiguration(extension.getAssembly());
    }

    private void configureBuildTask(Build task) {
        setGroup(task);
        task.dependsOn("test", "assemble");
    }

    private void setGroup(DefaultTask task) {
        task.setGroup("delphi");
    }

    private void setGroupInfo(DefaultTask task) {
        task.setGroup("delphi info");
    }

    private void setGroupHelp(DefaultTask task) {
        task.setGroup("delphi help");
    }
}
