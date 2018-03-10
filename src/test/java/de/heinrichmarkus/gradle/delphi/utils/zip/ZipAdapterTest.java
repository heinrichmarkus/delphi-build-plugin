package de.heinrichmarkus.gradle.delphi.utils.zip;

import de.heinrichmarkus.gradle.delphi.extensions.assambly.AssemblyItem;
import de.heinrichmarkus.gradle.delphi.utils.zip.ZipAdapter;
import de.heinrichmarkus.gradle.delphi.utils.zip.ZipFileMapping;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ZipAdapterTest {
    @Test
    public void toMappingSimpleFile() {
        test(new AssemblyItem("src/test/resources/zip/File1.txt"), "File1.txt");
    }

    @Test
    public void toMappingSimpleFileAnotherName() {
        String anotherFileName = "AnotherFileName.dat";
        AssemblyItem item = new AssemblyItem("src/test/resources/zip/File1.txt", anotherFileName);
        test(item, anotherFileName);
    }

    @Test
    public void toMappingSimpleFileToSubdir() {
        AssemblyItem item = new AssemblyItem("src/test/resources/zip/File1.txt", "subdir/");
        test(item, "subdir/File1.txt");
    }

    @Test
    public void toMappingSimpleFileToSubdirInclFilename() {
        AssemblyItem item = new AssemblyItem("src/test/resources/zip/File1.txt", "subdir/abc.txt");
        test(item, "subdir/abc.txt");
    }

    @Test
    public void toMappingSimpleDir() {
        AssemblyItem item = new AssemblyItem("src/test/resources/zip/Dir");
        test(item, "File2.txt", "Subdir/File3.txt");
    }

    @Test
    public void toMappingSimpleDirToSubdir() {
        AssemblyItem item = new AssemblyItem("src/test/resources/zip/Dir", "Dest");
        test(item, "Dest/File2.txt", "Dest/Subdir/File3.txt");
    }


    private void test(AssemblyItem item, String... destFileNames) {
        List<AssemblyItem> list = new ArrayList<>();
        list.add(item);
        List<ZipFileMapping> mappings = ZipAdapter.toMapping(list);
        assertEquals(destFileNames.length, mappings.size());
        for (int i = 0; i < destFileNames.length; i++) {
            String expected = destFileNames[i].replace("/", File.separator);
            String actual = mappings.get(i).getDestFileName();
            assertEquals(expected, actual);
        }
    }
}