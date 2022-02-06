package de.heinrichmarkus.gradle.delphi.utils.zip;

import de.heinrichmarkus.gradle.delphi.utils.ProjectDir;

import java.io.File;

class ZipFileMapping {
    private File sourceFile;
    private String destFileName;

    public ZipFileMapping(File sourceFile, String destFileName) {
        this.sourceFile = sourceFile;
        this.destFileName = destFileName;
    }

    public ZipFileMapping(String sourceFile, String destFileName) {
        this(ProjectDir.getInstance().newFile(sourceFile), destFileName);
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
