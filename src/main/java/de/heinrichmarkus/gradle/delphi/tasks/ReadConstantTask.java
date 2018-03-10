package de.heinrichmarkus.gradle.delphi.tasks;

import de.heinrichmarkus.gradle.delphi.tasks.base.DefaultConstantTask;
import de.heinrichmarkus.gradle.delphi.utils.delphi.DelphiUnit;
import org.gradle.api.tasks.TaskAction;

public class ReadConstantTask extends DefaultConstantTask {
    @TaskAction
    public void readConstant() {
        DelphiUnit unit = createDelphiUnit();
        getLogger().lifecycle(unit.readConstant(constantName.get()));
    }
}
