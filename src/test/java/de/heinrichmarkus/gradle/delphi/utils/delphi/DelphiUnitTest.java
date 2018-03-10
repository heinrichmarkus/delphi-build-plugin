package de.heinrichmarkus.gradle.delphi.utils.delphi;

import de.heinrichmarkus.gradle.delphi.utils.delphi.DelphiUnit;
import de.heinrichmarkus.gradle.delphi.utils.exceptions.UnitNotFoundException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static org.junit.Assert.*;

public class DelphiUnitTest {
    File fileConstants = new File("build/resources/test/Constants.pas");

    @Test
    public void testReadConstant() {
        DelphiUnit unit = new DelphiUnit(fileConstants);
        assertEquals("1.43.9-2017-09-30_212612", unit.readConstant("PROGRAM_VERSION"));
    }

    @Test
    public void testReadConstantFileDoesNotExist() {
        DelphiUnit unit = new DelphiUnit(new File("doesnotexist.pas"));
        try {
            unit.readConstant("PROGRAM_VERSION");
            fail();
        } catch (UnitNotFoundException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testWriteConstant() throws IOException {
        File fileConstantsCopy = new File("build/resources/test/Constants-copy.pas");
        Files.copy(fileConstants.toPath(), fileConstantsCopy.toPath(), StandardCopyOption.REPLACE_EXISTING);
        DelphiUnit unit = new DelphiUnit(fileConstantsCopy);
        String name = "PROGRAM_VERSION";
        String value = "abc";
        unit.writeConstant(name, value);
        assertEquals(value, unit.readConstant(name));
    }
}