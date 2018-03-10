package de.heinrichmarkus.gradle.delphi.tasks.base;

import de.heinrichmarkus.gradle.delphi.utils.delphi.DelphiUnit;
import org.gradle.api.provider.PropertyState;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

public abstract class DefaultWriteConstantTask extends DefaultConstantTask {
    final PropertyState<Boolean> disabled = getProject().property(Boolean.class);

    @TaskAction
    public void writeConstant() {
        if (!disabled.get() && !constantName.get().isEmpty()) {
            String value = calculateValue();
            DelphiUnit unit = createDelphiUnit();
            unit.writeConstant(constantName.get(), value);
            print(value);
        } else {
            getLogger().lifecycle("Skipped");
        }
    }

    private void print(String value) {
        getLogger().lifecycle(String.format("Write '%s' to %s:%s", value, fileName.get(), constantName.get()));
    }

    protected abstract String calculateValue();

    @Input
    public PropertyState<Boolean> getDisabled() {
        return disabled;
    }


}
