package de.heinrichmarkus.gradle.delphi.utils.delphi;

import de.heinrichmarkus.gradle.delphi.utils.Utils;
import de.heinrichmarkus.gradle.delphi.utils.exceptions.ConstantNotFoundException;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DelphiUnit {
    private final File file;
    private final Charset charset = StandardCharsets.ISO_8859_1;

    public DelphiUnit(File file) {
        this.file = file;
    }

    public String readConstant(String constantName) {
        List<String> lines = Utils.readAllLines(file, charset);
        for (String l : lines) {
            Matcher matcher = getPattern(constantName).matcher(l);
            if (matcher.matches()) {
                return matcher.group(1);
            }
        }
        throw getNotFoundException(file, constantName);
    }

    public void writeConstant(String name, String value) {
        List<String> lines = Utils.readAllLines(file, charset);
        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            Matcher matcher = getPattern(name).matcher(line);
            if (matcher.matches()) {
                line = line.replace(matcher.group(1), value);
                lines.set(i, line);
                found = true;
            }
        }
        if (!found) {
            throw getNotFoundException(file, name);
        }
        Utils.writeAllLines(file, lines, charset);
    }

    private Pattern getPattern(String constantName) {
        return Pattern.compile("^\\s*" + constantName + "\\s*=\\s*'(.*)';");
    }

    private ConstantNotFoundException getNotFoundException(File file, String constantName) {
        return new ConstantNotFoundException(String.format("Constant '%s' not found in file: %s", constantName, file.toString()));
    }

}
