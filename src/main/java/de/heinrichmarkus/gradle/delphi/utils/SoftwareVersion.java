package de.heinrichmarkus.gradle.delphi.utils;

import de.heinrichmarkus.gradle.delphi.utils.exceptions.SoftwareVersionParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SoftwareVersion {
    private int major;
    private int minor;
    private int patch;
    private Calendar date = new GregorianCalendar(1899, 11, 30, 12, 0);

    public SoftwareVersion(String version) {
        Pattern pattern = Pattern.compile("^(\\d+)\\.(\\d+)\\.(\\d+)\\-?(\\d{4}-\\d{2}-\\d{2}_\\d{6})?$");
        Matcher matcher = pattern.matcher(version);
        if (matcher.matches()) {
            major = Integer.parseInt(matcher.group(1));
            minor = Integer.parseInt(matcher.group(2));
            patch = Integer.parseInt(matcher.group(3));
            if (matcher.group(4) != null) {
                date = parseDate(matcher.group(4));
            }
        } else {
            throw new SoftwareVersionParseException(
                    String.format("Software-Version '%s' is invalid. Use right format e.g.: 1.14.2-2017-06-16_084521",
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
        String dateStr = sdf.format(date.getTime());
        return String.format("%d.%d.%d-%s", major, minor, patch, dateStr);
    }

    public String format() {
        return format(Format.FULL);
    }

    public String format(Format format) {
        switch (format) {
            case FULL: return toString();
            case SHORT: return String.format("%d.%d.%d", major, minor, patch);
            default: throw new IllegalArgumentException("Illegal Format");
        }
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

    public enum Format {
        SHORT,
        FULL
    }
}
