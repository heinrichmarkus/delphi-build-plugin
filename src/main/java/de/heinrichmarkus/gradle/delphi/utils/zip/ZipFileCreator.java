package de.heinrichmarkus.gradle.delphi.utils.zip;

import de.heinrichmarkus.gradle.delphi.utils.ProjectDir;
import de.heinrichmarkus.gradle.delphi.utils.exceptions.CreateZipException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

class ZipFileCreator {

    private ZipFileCreator() {
        // Hide Constructor
    }

    public static void create(List<ZipFileMapping> mappings, String destFile) {
        create(mappings, ProjectDir.getInstance().newFile(destFile));
    }

    public static void create(List<ZipFileMapping> mappings, File destFile) {
        try {
            try (FileOutputStream fos = new FileOutputStream(destFile))
            {
                ZipOutputStream zipOutputStream = new ZipOutputStream(fos);
                for (ZipFileMapping m : mappings)
                {
                    addToZip(m, zipOutputStream);
                }
                zipOutputStream.close();
            }
        } catch (IOException e) {
            throw new CreateZipException(e);
        }
    }

    private static void addToZip(ZipFileMapping mapping, ZipOutputStream zipOutputStream) throws IOException {
	    try (FileInputStream fis = new FileInputStream(mapping.getSourceFile()))
	    {
		    ZipEntry zipEntry = new ZipEntry(mapping.getDestFileName());
		    zipOutputStream.putNextEntry(zipEntry);

		    byte[] bytes = new byte[1024];
		    int length;
		    while ((length = fis.read(bytes)) >= 0)
		    {
			    zipOutputStream.write(bytes, 0, length);
		    }

		    zipOutputStream.closeEntry();
	    }
    }
}
