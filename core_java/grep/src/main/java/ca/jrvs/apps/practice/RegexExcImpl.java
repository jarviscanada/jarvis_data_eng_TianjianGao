package ca.jrvs.apps.practice;

import java.util.regex.Pattern;

public class RegexExcImpl implements RegexExc {

    // The pattern (?i).*\\.(jpg|jpeg)$ is used to match file names ending with .jpg
    // or .jpeg. The (?i) makes the match case-insensitive.
    private static final Pattern JPEG_PATTERN = Pattern.compile("(?i).*\\.(jpg|jpeg)$");
    // The pattern ^([0-9]{1,3}\\.){3}[0-9]{1,3}$ matches IP addresses in the range
    // 0.0.0.0 to 999.999.999.999. It matches exactly three groups of digits
    // followed by a dot, and then one more group of digits.
    private static final Pattern IP_PATTERN = Pattern.compile("^([0-9]{1,3}\\.){3}[0-9]{1,3}$");
    // The pattern ^\\s*$ matches any string that consists only of whitespace
    // characters (spaces, tabs, etc.) or is completely empty.
    private static final Pattern EMPTY_LINE_PATTERN = Pattern.compile("^\\s*");

    @Override
    public boolean matchJpeg(String filename) {
        return JPEG_PATTERN.matcher(filename).matches();
    }

    @Override
    public boolean matchIp(String ip) {
        return IP_PATTERN.matcher(ip).matches();
    }

    @Override
    public boolean isEmptyLine(String line) {
        return EMPTY_LINE_PATTERN.matcher(line).matches();
    }

}
