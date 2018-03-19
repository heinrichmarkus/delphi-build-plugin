package de.heinrichmarkus.gradle.delphi.utils.environment;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnvVars {
    private final Map<String, String> vars = new LinkedHashMap<>();

    public void put(String key, String value) {
        if (exists(key)) {
            value = substitute(value, key);
            vars.remove(key);
        }
        vars.put(toVarName(key), value);
    }

    public void put(Map<String, String> map) {
        for (Map.Entry<String, String> e : map.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    private boolean exists(String varName) {
        return vars.containsKey(toVarName(varName));
    }

    public String get(String key) {
        String varName = toVarName(key);
        if (!vars.containsKey(varName)) {
            throw new EnvironmentVariableNotFound(String.format("Environment variable \"%s\" not found", varName));
        }
        return vars.get(varName);
    }

    private String toVarName(String key) {
        return key.toUpperCase().trim();
    }

    public String resolve(String key) {
        String varValue = get(key);
        List<String> varNamesToSubstitute = getVarNamesToSubstitute(varValue);
        for (String varNameToSubstitute : varNamesToSubstitute) {
            if (exists(varNameToSubstitute)) {
                varValue = substitute(varValue, varNameToSubstitute);
            }
        }
        return varValue;
    }

    private List<String> getVarNamesToSubstitute(String value) {
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile(".*%(\\w+)%.*");
        Matcher matcher;
        String tmp = value;
        while ((matcher = pattern.matcher(tmp)).matches()) {
            String varName = matcher.group(1);
            list.add(varName);
            tmp = tmp.replace(String.format("%%%s%%", varName), "");
        }
        return list;
    }

    private String substitute(String value, String varName) {
        if (exists(varName)) {
            String varValueToSubstitute = get(varName);
            return value.replace(String.format("%%%s%%", varName), varValueToSubstitute);
        } else {
            return value;
        }
    }

    public Map<String, String> toMap() {
        return Collections.unmodifiableMap(vars);
    }

    public List<String> toList() {
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, String> e : vars.entrySet()) {
            list.add(String.format("%s=%s", e.getKey(), resolve(e.getKey())));
        }
        Collections.sort(list);
        return list;
    }
}
