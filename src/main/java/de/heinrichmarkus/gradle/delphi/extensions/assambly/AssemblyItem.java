package de.heinrichmarkus.gradle.delphi.extensions.assambly;

import de.heinrichmarkus.gradle.delphi.extensions.assambly.path.DirTranslationStrategy;
import de.heinrichmarkus.gradle.delphi.extensions.assambly.path.FileTranslationStrategy;
import de.heinrichmarkus.gradle.delphi.extensions.assambly.path.TranslationStrategy;

import java.io.File;

public class AssemblyItem {
    private String source;
    private String destination;
    private Boolean optional;

    public AssemblyItem(String source) {
        this(source, "");
    }

    public  AssemblyItem(String source, Boolean optional) {
        this(source, "", optional);
    }

    public AssemblyItem(String source, String destination) {
        this(source, destination, false);
    }

    public AssemblyItem(String source, String destination, Boolean optional) {
        this.source = source;
        this.destination = destination;
        this.optional = optional;
    }

    public boolean isDir() {
        File f = new File(source);
        return f.isDirectory();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    public String translateFilename(File file) {
        if (!file.isFile()) {
            throw new TranslateZipPathException(String.format("'%s' ist not a file", file.toString()));
        }

        TranslationStrategy strategy;
        if (isDir()) {
            strategy = new DirTranslationStrategy(this);
        } else {
            strategy = new FileTranslationStrategy(this);
        }
        return strategy.translateFilename(file);
    }
}
