package de.heinrichmarkus.gradle.delphi.extensions.assambly.path;

import de.heinrichmarkus.gradle.delphi.extensions.assambly.AssemblyItem;

import java.io.File;
import java.nio.file.Path;

public class DirTranslationStrategy extends BaseTranslationStrategy {
    public DirTranslationStrategy(AssemblyItem item) {
        super(item);
    }

    @Override
    public String translateFilename(File sourceFile) {
        File f = removeSourceDir(sourceFile);
        f = addDestinationDir(f);
        return f.getPath();
    }

    private File removeSourceDir(File sourceFile) {
        Path absoluteSourceDir = new File(item.getSource()).getAbsoluteFile().toPath();
        Path absoluteSourceFile = sourceFile.getAbsoluteFile().toPath();
        Path rp = absoluteSourceDir.relativize(absoluteSourceFile);
        return rp.toFile();
    }

    private File addDestinationDir(File sourceFile) {
        if (item.getDestination() != null && !item.getDestination().isEmpty()) {
            File f = new File(item.getDestination());
            return new File(f, sourceFile.getPath());
        } else {
            return sourceFile;
        }
    }
}
