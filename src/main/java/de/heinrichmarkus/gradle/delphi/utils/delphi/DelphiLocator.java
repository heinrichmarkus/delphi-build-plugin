package de.heinrichmarkus.gradle.delphi.utils.delphi;

import de.heinrichmarkus.gradle.delphi.utils.Utils;
import de.heinrichmarkus.gradle.delphi.utils.exceptions.CouldNotExtractBDSVersion;
import de.heinrichmarkus.gradle.delphi.utils.exceptions.CouldNotExtractRootDirException;
import de.heinrichmarkus.gradle.delphi.utils.exceptions.DelphiVersionNotFoundException;
import de.heinrichmarkus.gradle.delphi.utils.exceptions.RegistryReadException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DelphiLocator {
    private DelphiLocator() {
        // hide constructor
    }

    public static File locateInstallation(String bdsVersion) {
        List<DelphiInstallation> installations = locateInstallations();
        for (DelphiInstallation inst : installations) {
            if (inst.getVersion().equalsIgnoreCase(bdsVersion)) {
                return new File(inst.getRootDir());
            }
        }
        throw new DelphiVersionNotFoundException(String.format("Delphi installation (BDS %s) not found", bdsVersion));
    }

    public static List<DelphiInstallation> locateInstallations() {
        List<String> versionKeys = readVersionKeys();
        return generateDelphiInstallationObjects(versionKeys);
    }

    private static List<String> readVersionKeys() {
        String command = "reg query HKCU\\Software\\Embarcadero\\BDS";
        return exec(command);
    }

    private static List<DelphiInstallation> generateDelphiInstallationObjects(List<String> subKeys) {
        // HKEY_CURRENT_USER\Software\Embarcadero\BDS\19.0
        List<DelphiInstallation> installations = new ArrayList<>();
        for (String key : subKeys) {
            if (!key.trim().isEmpty()) {
                installations.add(generateDelphiInstallation(key));
            }
        }
        return installations;
    }

    private static DelphiInstallation generateDelphiInstallation(String key) {
        String version = extractVersion(key);
        String rootDir = readRootDir(key);
        return new DelphiInstallation(version, rootDir);
    }

    protected static String extractVersion(String key) {
        Pattern pattern = Pattern.compile(".*BDS\\\\(.*)");
        Matcher matcher = pattern.matcher(key);
        if (matcher.matches()) {
            return matcher.group(1);
        } else {
            throw new CouldNotExtractBDSVersion(key);
        }
    }

    private static String readRootDir(String key) {
        String command = String.format("reg query %s /v RootDir", key);
        List<String> lines = exec(command);
        return extractRootDir(lines);
    }

    protected static String extractRootDir(List<String> lines) {
        Pattern pattern = Pattern.compile(".*REG_SZ\\s+(.*)");
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.matches()) {
                return matcher.group(1);
            }
        }
        throw new CouldNotExtractRootDirException(Utils.concat(lines));
    }

    private static List<String> exec(String command) {
        List<String> list = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
            return list;
        } catch (IOException e) {
            throw new RegistryReadException(e);
        }
    }
}
