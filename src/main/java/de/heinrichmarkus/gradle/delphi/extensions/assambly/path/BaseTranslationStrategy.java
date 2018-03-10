package de.heinrichmarkus.gradle.delphi.extensions.assambly.path;

import de.heinrichmarkus.gradle.delphi.extensions.assambly.AssemblyItem;

import java.io.File;

public abstract class BaseTranslationStrategy implements TranslationStrategy {
    protected AssemblyItem item;

    public BaseTranslationStrategy(AssemblyItem item) {
        this.item = item;
    }

    public abstract String translateFilename(File sourceFile);
}
