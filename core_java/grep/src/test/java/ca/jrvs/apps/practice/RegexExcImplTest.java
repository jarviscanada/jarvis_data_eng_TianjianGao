package ca.jrvs.apps.practice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RegexExcImplTest {

    private final RegexExc regexExc = new RegexExcImpl();

    @Test
    public void testMatchJpeg() {
        assertTrue(regexExc.matchJpeg("image.jpg"));
        assertTrue(regexExc.matchJpeg("photo.jpeg"));
        assertTrue(regexExc.matchJpeg("PICTURE.JPG"));
        assertTrue(regexExc.matchJpeg("somefile.JPEG"));

        assertFalse(regexExc.matchJpeg("image.png"));
        assertFalse(regexExc.matchJpeg("photo.jpgg"));
        assertFalse(regexExc.matchJpeg("picture.JPgx"));
        assertFalse(regexExc.matchJpeg("somefilejpeg")); // Missing dot
    }

    @Test
    public void testMatchIp() {
        assertTrue(regexExc.matchIp("192.168.0.1"));
        assertTrue(regexExc.matchIp("255.255.255.255"));
        assertTrue(regexExc.matchIp("0.0.0.0"));
        assertTrue(regexExc.matchIp("999.999.999.999"));
        assertTrue(regexExc.matchIp("256.256.256.256")); // Out of typical IP range but allowed by regex

        assertFalse(regexExc.matchIp("192.168.0"));
        assertFalse(regexExc.matchIp("1234.567.89.0")); // Too many digits
        assertFalse(regexExc.matchIp("192.168.0.01.1")); // Too many segments
    }

    @Test
    public void testIsEmptyLine() {
        assertTrue(regexExc.isEmptyLine(""));
        assertTrue(regexExc.isEmptyLine("   ")); // Spaces
        assertTrue(regexExc.isEmptyLine("\t")); // Tab
        assertTrue(regexExc.isEmptyLine("\n")); // Newline
        assertTrue(regexExc.isEmptyLine("\r\n")); // Windows-style newline

        assertFalse(regexExc.isEmptyLine("text"));
        assertFalse(regexExc.isEmptyLine("   text   ")); // Text with spaces
        assertFalse(regexExc.isEmptyLine("\ttext")); // Text with tab
    }

}
