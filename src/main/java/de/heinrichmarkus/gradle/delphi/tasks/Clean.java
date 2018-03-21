package de.heinrichmarkus.gradle.delphi.tasks;

import de.heinrichmarkus.gradle.delphi.utils.Utils;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

public class Clean extends DefaultTask {
    private final Property<String> binDirectory = getProject().getObjects().property(String.class);

    @TaskAction
    public void cleanup() {
        File bin = new File(binDirectory.get());
        if (bin.exists()) {
            long count = Utils.countFiles(bin);
            Utils.deleteDir(bin);
            getLogger().lifecycle(String.format("Cleaned up %d files", count));
        } else {
            getLogger().lifecycle("Everything's already clean");
        }
    }

    @Input
    public Property<String> getBinDirectory() {
        return binDirectory;
    }
}
