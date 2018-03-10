package de.heinrichmarkus.gradle.delphi.utils;

import de.heinrichmarkus.gradle.delphi.utils.exceptions.ReadCommitIdException;

import java.io.File;

public class GitCommitReader {
    private GitCommitReader() {
        // Hide constructor
    }

    public static String readCommit(File head) {
        throwExceptionIfNotExists(head);
        return readCommitFromFile(head);
    }

    private static String readCommitFromFile(File head) {
        String fileContent = Utils.readText(head);
        if (isReference(fileContent)) {
            File refFile = getReferencedFile(head, fileContent);
            return readCommitFromFile(refFile);
        } else {
            return fileContent;
        }
    }

    private static File getReferencedFile(File file, String fileContent) {
        String referencedFile = fileContent.replace("ref: ", "");
        return new File(file.getParentFile(), referencedFile);
    }

    private static boolean isReference(String fileContent) {
        return fileContent.startsWith("ref: ");
    }

    private static void throwExceptionIfNotExists(File head) {
        if (!head.exists()) {
            throw new ReadCommitIdException(
                    String.format("File '%s' does not exist!", head.getAbsoluteFile().toString()));
        }
    }
}
