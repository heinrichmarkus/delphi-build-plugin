package de.heinrichmarkus.gradle.delphi.utils;

import de.heinrichmarkus.gradle.delphi.utils.exceptions.CommandExecException;
import de.heinrichmarkus.gradle.delphi.utils.exceptions.UnitNotFoundException;
import de.heinrichmarkus.gradle.delphi.utils.exceptions.UtilsIOException;
import de.heinrichmarkus.gradle.delphi.utils.logger.DbpLogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Utils {
    public static List<String> readAllLines(File file) {
        return readAllLines(file, StandardCharsets.UTF_8);
    }

    public static List<String> readAllLines(File file, Charset charset) {
        return (List<String>) execute(file, () -> Files.readAllLines(file.getAbsoluteFile().toPath(), charset));
    }

    public static void writeAllLines(File file, List<String> lines) {
        writeAllLines(file, lines, StandardCharsets.UTF_8);
    }

    public static void writeAllLines(File file, List<String> lines, Charset charset) {
        execute(file, () -> { Files.write(file.getAbsoluteFile().toPath(), lines, charset); return null; });
    }

    public static void appendAllLines(File file, List<String> lines) {
        execute(file, () -> { Files.write(file.getAbsoluteFile().toPath(), lines, StandardOpenOption.APPEND); return null; });
    }

    private static Object execute(File file, IOOperation operation) {
        try {
            if (!file.exists()) {
                throw new UnitNotFoundException("Unit " + file.getAbsolutePath() + " doesn't exist");
            }
            return operation.execute();
        } catch (IOException e) {
            throw new UtilsIOException(e);
        }
    }

    public static String readText(File file) {
        StringBuilder text = new StringBuilder();
        List<String> lines = readAllLines(file);
        for (String line : lines) {
            text.append(line);
        }
        return text.toString();
    }

    public static long countFiles(File dir) {
        long count = 0;
        File[] files = dir.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                count += countFiles(f);
            } else {
                count++;
            }
        }
        return count;
    }

    public static String concat(List<String> lines) {
        return String.valueOf(lines);
    }

    public static void deleteDir(File dir) {
        File[] files = dir.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                f.delete();
            } else {
                deleteDir(f);
            }
        }
        dir.delete();
    }

    public static int exec(String command, List<String> envVars, File dir, DbpLogger logger) {
        try {
            Process process = Runtime.getRuntime().exec(command, envVars.toArray(new String[0]), dir);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                logger.log(line);
            }
            process.waitFor();
            return process.exitValue();
        } catch (IOException | InterruptedException e) {
            throw new CommandExecException(e);
        }
    }

    private interface IOOperation {
        Object execute() throws IOException;
    }
}
