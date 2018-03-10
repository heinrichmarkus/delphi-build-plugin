package de.heinrichmarkus.gradle.delphi.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class Build extends DefaultTask {
    @TaskAction
    public void build() {
        System.out.println("build");
    }
}
