package de.heinrichmarkus.gradle.delphi.utils;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class GitCommitReaderTest {
    @Test
    public void testReadCommit() {
        assertEquals("5bf16a5c34961442bdf50293c0fdfe25621fd7f4",
                GitCommitReader.readCommit(new File("build/resources/test/git/HEAD_REF")));
    }

    @Test
    public void testReadCommitDetached() {
        assertEquals("5bf16a5c34961442bdf50293c0fdfe25621fd7f4",
                GitCommitReader.readCommit(new File("build/resources/test/git/HEAD_DETACHED")));
    }
}