package de.heinrichmarkus.gradle.delphi.extensions.assambly.path;

import de.heinrichmarkus.gradle.delphi.extensions.assambly.AssemblyItem;

import java.io.File;

public abstract class BaseTranslationStrategy implements TranslationStrategy {
    final AssemblyItem item;

    BaseTranslationStrategy(AssemblyItem item) {
        this.item = item;
    }

    public abstract String translateFilename(File sourceFile);
}
