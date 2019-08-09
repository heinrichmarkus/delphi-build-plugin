package de.heinrichmarkus.gradle.delphi.tasks;

import de.heinrichmarkus.gradle.delphi.extensions.assambly.AssemblyConfiguration;
import de.heinrichmarkus.gradle.delphi.extensions.assambly.AssemblyItem;
import de.heinrichmarkus.gradle.delphi.tasks.exceptions.AssemblyCreateException;
import de.heinrichmarkus.gradle.delphi.utils.SoftwareVersion;
import de.heinrichmarkus.gradle.delphi.utils.zip.ZipAdapter;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Assemble extends DefaultTask {
    private final Property<String> bin = getProject().getObjects().property(String.class);
    private final Property<String> version = getProject().getObjects().property(String.class);
    private AssemblyConfiguration assemblyConfiguration;

    @TaskAction
    public void assemble() {
        if (!assemblyConfiguration.getItems().isEmpty()) {
            File destFile = calcDestFile();
            deleteIfExists(destFile);
            reportOptionalMissingFiles(assemblyConfiguration.getItems());
            ZipAdapter.create(assemblyConfiguration.getItems(), destFile);
            getLogger().lifecycle(String.format("%s created", destFile));
        } else {
            getLogger().warn("Nothing to assemble!");
        }
    }

    private File calcDestFile() {
        SoftwareVersion v = new SoftwareVersion(version.get());
        return new File(bin.get(), String.format("%s-%s.zip", assemblyConfiguration.getName().get(),
                v.format(SoftwareVersion.Format.SHORT)));
    }

    private void deleteIfExists(File destFile) {
        if (destFile.exists()) {
            try {
                Files.delete(destFile.getAbsoluteFile().toPath());
            } catch (IOException e) {
                throw new AssemblyCreateException(
                        String.format("Couldn't delete old assembly %s", destFile.getAbsolutePath()), e);
            }
        }
    }

    private void reportOptionalMissingFiles(List<AssemblyItem> items) {
        for (AssemblyItem i : items) {
            File f = new File(i.getSource());
            if (i.getOptional() && !f.exists()) {
                getLogger().lifecycle(String.format("Skip %s", f.getName()));
            }
        }
    }

    @Input
    public Property<String> getBin() {
        return bin;
    }

    @Input
    public Property<String> getVersion() {
        return version;
    }

    @Input
    public AssemblyConfiguration getAssemblyConfiguration() {
        return assemblyConfiguration;
    }

    public void setAssemblyConfiguration(AssemblyConfiguration assemblyConfiguration) {
        this.assemblyConfiguration = assemblyConfiguration;
    }
}
