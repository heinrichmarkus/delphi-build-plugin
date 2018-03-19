package de.heinrichmarkus.gradle.delphi.utils.environment;

import de.heinrichmarkus.gradle.delphi.utils.Utils;

import java.io.File;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RsVarsReader {
    private final File rsvars;
    private final Map<String, String> envVars;

    public RsVarsReader(File rsvars) {
        this.rsvars = rsvars;
        envVars = getEnvVars();
    }

    public String read(String name) {
        if (!envVars.containsKey(name)) {
            throw new EnvironmentVariableNotFound(String.format("Variablenname '%s'", name));
        }
        return envVars.get(name);
    }

    public Map<String, String> toMap() {
        return Collections.unmodifiableMap(envVars);
    }

    private Map<String, String> getEnvVars() {
        List<String> lines = Utils.readAllLines(rsvars);
        Map<String, String> map = new LinkedHashMap<>();
        Pattern pattern = Pattern.compile(".+ (\\w+)=(.*)");
        for (String line : lines)
        {
            Matcher matcher = pattern.matcher(line);
            if (matcher.matches())
            {
                map.put(matcher.group(1), matcher.group(2));
            }
        }
        return map;
    }

    public int size() {
        return envVars.size();
    }
}
