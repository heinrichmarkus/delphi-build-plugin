package de.heinrichmarkus.gradle.delphi.utils.zip;

import de.heinrichmarkus.gradle.delphi.utils.zip.ZipFileCreator;
import de.heinrichmarkus.gradle.delphi.utils.zip.ZipFileMapping;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.junit.Assert.assertTrue;

public class ZipFileCreatorTest {
    @Test
    public void create() throws Exception {
        String zipFilename = "build/resources/test/test-zip.zip";
        List<ZipFileMapping> mappings = new ArrayList<>();
        mappings.add(new ZipFileMapping("src/test/resources/zip/File1.txt", "File1.txt"));
        mappings.add(new ZipFileMapping("src/test/resources/zip/File1.txt", "sub/abc.txt"));
        ZipFileCreator.create(mappings, zipFilename);

        expect(zipFilename, "File1.txt", "sub/abc.txt");
    }

    private void expect(String zipFilename, String... expectedFiles) throws Exception {
        ZipFile zipFile = new ZipFile(zipFilename);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        for (String filename : expectedFiles) {
            ZipEntry entry = zipFile.getEntry(filename);
            assertTrue(filename, entry.getSize() > 0);
        }
        assertTrue(true);
    }

}