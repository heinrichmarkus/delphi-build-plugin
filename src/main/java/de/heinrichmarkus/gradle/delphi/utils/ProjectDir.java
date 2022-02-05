package de.heinrichmarkus.gradle.delphi.utils;

import java.io.File;

public class ProjectDir {
    private static ProjectDir instance = null;
    private File dir;

    private ProjectDir() {
        // Singleton
    }

    public static ProjectDir getInstance() {
        if (instance == null) {
            instance = new ProjectDir();
        }
        return instance;
    }

    public File newFile(String child) {
        return new File(dir, child);
    }

    public File newFile(String child1, String child2) {
        return new File(newFile(child1), child2);
    }

    public File getDir() {
        return dir;
    }

    public void setDir(File dir) {
        this.dir = dir;
    }
}
