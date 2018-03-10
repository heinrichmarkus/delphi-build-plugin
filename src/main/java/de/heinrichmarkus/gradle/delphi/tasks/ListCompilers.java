package de.heinrichmarkus.gradle.delphi.tasks;

import de.heinrichmarkus.gradle.delphi.utils.delphi.DelphiInstallation;
import de.heinrichmarkus.gradle.delphi.utils.delphi.DelphiLocator;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.util.List;

public class ListCompilers extends DefaultTask {
    @TaskAction
    private void list() {
        List<DelphiInstallation> installations = DelphiLocator.locateInstallations();
        if (!installations.isEmpty()) {
            for (DelphiInstallation i : installations) {
                getLogger().lifecycle(i.toString());
            }
        } else {
            getLogger().lifecycle("No installations found");
        }
    }
}
