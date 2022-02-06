package de.heinrichmarkus.gradle.delphi.utils;

import de.heinrichmarkus.gradle.delphi.utils.exceptions.SoftwareVersionParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SoftwareVersion {
    private int major = 0;
    private int minor = 0;
    private int patch = 0;
    private int build = 0;
    private boolean printBuildNumber = false;
    private Calendar date = new GregorianCalendar(1899, 11, 30, 12, 0);

    public SoftwareVersion(String version) {
        Pattern pattern = Pattern.compile("^(\\d+)\\.(\\d+)\\.(\\d+)(\\.\\d+)?-?(\\d{4}-\\d{2}-\\d{2}_\\d{6})?$");
        Matcher matcher = pattern.matcher(version);
        if (matcher.matches()) {
            major = Integer.parseInt(matcher.group(1));
            minor = Integer.parseInt(matcher.group(2));
            patch = Integer.parseInt(matcher.group(3));
            if (matcher.group(4) != null) {
                printBuildNumber = true;
                build = Integer.parseInt(matcher.group(4).replace(".", ""));
            }
            if (matcher.group(5) != null) {
                date = parseDate(matcher.group(5));
            }
        } else {
            throw new SoftwareVersionParseException(
                    String.format("Software-Version '%s' is invalid. Check format (e.g.: 1.14.2-2017-06-16_084521).",
                            version));
        }
    }

    private Calendar parseDate(String datePart) {
        Pattern pattern = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})_(\\d{2})(\\d{2})(\\d{2})");
        Matcher matcher = pattern.matcher(datePart);
        if (matcher.matches()) {
            int year = Integer.parseInt(matcher.group(1));
            int month = Integer.parseInt(matcher.group(2));
            int day = Integer.parseInt(matcher.group(3));
            int hour = Integer.parseInt(matcher.group(4));
            int minute = Integer.parseInt(matcher.group(5));
            int second = Integer.parseInt(matcher.group(6));
            return new GregorianCalendar(year, month - 1, day, hour, minute, second);
        } else {
            throw new SoftwareVersionParseException(String.format("Date format is invalid (%s)", datePart));
        }
    }

    @Override
    public String toString() {
        return format(Format.FULL);
    }

    public String format() {
        return format(Format.FULL);
    }

    public String format(Format format) {
        String result = "";
        if (printBuildNumber) {
            result = String.format("%d.%d.%d.%d", major, minor, patch, build);
        } else {
            result = String.format("%d.%d.%d", major, minor, patch);
        }

        if (format == Format.FULL) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
            String dateStr = sdf.format(date.getTime());
            result += String.format("-%s", dateStr);
        }
        return result;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getPatch() {
        return patch;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public int getBuild() {
        return build;
    }

    public void setBuild(int build) {
        this.printBuildNumber = true;
        this.build = build;
    }

    public boolean isPrintBuildNumber() {
        return printBuildNumber;
    }

    public void setPrintBuildNumber(boolean printBuildNumber) {
        this.printBuildNumber = printBuildNumber;
    }

    public enum Format {
        SHORT,
        FULL
    }
}
