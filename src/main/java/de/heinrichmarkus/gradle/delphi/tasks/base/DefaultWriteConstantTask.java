package de.heinrichmarkus.gradle.delphi.tasks.base;

import de.heinrichmarkus.gradle.delphi.utils.delphi.DelphiUnit;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

public abstract class DefaultWriteConstantTask extends DefaultConstantTask {
    private final Property<Boolean> disabled = getProject().getObjects().property(Boolean.class);

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
    public Property<Boolean> getDisabled() {
        return disabled;
    }


}
