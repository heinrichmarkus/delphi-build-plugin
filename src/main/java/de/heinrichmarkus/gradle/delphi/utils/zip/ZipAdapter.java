package de.heinrichmarkus.gradle.delphi.utils.zip;

import de.heinrichmarkus.gradle.delphi.extensions.assambly.AssemblyItem;
import de.heinrichmarkus.gradle.delphi.utils.exceptions.CreateZipException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ZipAdapter {

    public static void create(List<AssemblyItem> items, File destFile) {
        List<ZipFileMapping> mappings = toMapping(items);
        ZipFileCreator.create(mappings, destFile);
    }

    public static List<ZipFileMapping> toMapping(List<AssemblyItem> assemblyItems) {
        List<ZipFileMapping> list = new ArrayList<>();
        for (AssemblyItem item : assemblyItems) {
            list.addAll(toMapping(item));
        }
        return list;
    }

    private static List<ZipFileMapping> toMapping(AssemblyItem item) {
        File sourceFile = new File(item.getSource());
        if (sourceFile.exists()) {
            List<File> files = readFiles(sourceFile);
            return toMapping(item, files);
        } else {
            if (item.getOptional()) {
                return Collections.emptyList();
            } else {
                throw new CreateZipException(String.format("File '%s' does not exist", item.getSource()));
            }
        }
    }

    private static List<ZipFileMapping> toMapping(AssemblyItem item, List<File> files) {
        List<ZipFileMapping> mappings = new ArrayList<>();
        for (File f : files) {
            mappings.add(new ZipFileMapping(f, item.translateFilename(f)));
        }
        return mappings;
    }

    private static List<File> readFiles(File sourceFile) {
        List<File> files = new ArrayList<>();
        if (sourceFile.isDirectory()) {
            files.addAll(getAllFilesRecursive(sourceFile));
        } else {
            files.add(sourceFile);
        }
        return files;
    }

    private static List<File> getAllFilesRecursive(File sourceFile) {
        List<File> files = new ArrayList<>();
        File[] allFiles = sourceFile.listFiles();
        if (allFiles != null) {
            for (File f : allFiles) {
                if (f.isFile()) {
                    files.add(f);
                } else {
                    files.addAll(getAllFilesRecursive(f));
                }
            }
        }
        return files;
    }
}
