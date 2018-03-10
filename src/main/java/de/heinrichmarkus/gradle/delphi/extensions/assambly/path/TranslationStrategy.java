package de.heinrichmarkus.gradle.delphi.extensions.assambly.path;

import java.io.File;

public interface TranslationStrategy {
    String translateFilename(File sourceFile);
}
