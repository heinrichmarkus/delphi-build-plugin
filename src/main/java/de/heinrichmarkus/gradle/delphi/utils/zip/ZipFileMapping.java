package de.heinrichmarkus.gradle.delphi.utils.zip;

import java.io.File;

public class ZipFileMapping {
    private File sourceFile;
    private String destFileName;

    public ZipFileMapping(File sourceFile, String destFileName) {
        this.sourceFile = sourceFile;
        this.destFileName = destFileName;
    }

    public ZipFileMapping(String sourceFile, String destFileName) {
        this(new File(sourceFile), destFileName);
    }

    @Override
    public String toString() {
        return String.format("%s -> %s", sourceFile != null ? sourceFile.toString() : "null", destFileName);
    }

    public File getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public String getDestFileName() {
        return destFileName;
    }

    public void setDestFileName(String destFileName) {
        this.destFileName = destFileName;
    }
}
