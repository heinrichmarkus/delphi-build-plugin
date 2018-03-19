package de.heinrichmarkus.gradle.delphi.extensions.assambly.path;

import de.heinrichmarkus.gradle.delphi.extensions.assambly.AssemblyItem;
import de.heinrichmarkus.gradle.delphi.extensions.assambly.TranslateZipPathException;

import java.io.File;

public class FileTranslationStrategy extends BaseTranslationStrategy {

    public FileTranslationStrategy(AssemblyItem item) {
        super(item);
    }

    @Override
    public String translateFilename(File sourceFile) {
        if (!sourceFile.isFile()) {
            throw new TranslateZipPathException(String.format("'%s' ist not a file", sourceFile.toString()));
        }

        String path = getDestinationFilePath(sourceFile);
        File calculatedFile = new File(getDestinationFileName(sourceFile));
        if (path != null && !path.isEmpty()) {
            calculatedFile = new File(path, calculatedFile.getName());
        }
        return calculatedFile.toString();
    }

    private String getDestinationFilePath(File file) {
        if (containsDirName(item.getDestination())) {
            if (containsFileName(item.getDestination())) {
                return new File(item.getDestination()).getParent();
            } else {
                return new File(item.getDestination()).getPath();
            }
        } else {
            return "";
        }
    }

    private boolean containsDirName(String str) {
        return str != null && str.contains("/");
    }

    private String getDestinationFileName(File file) {
        if (containsFileName(item.getDestination())) {
            return new File(item.getDestination()).getName();
        } else {
            return file.getName();
        }
    }

    private boolean containsFileName(String str) {
        return str != null && !str.isEmpty() && !str.endsWith("/");
    }
}
