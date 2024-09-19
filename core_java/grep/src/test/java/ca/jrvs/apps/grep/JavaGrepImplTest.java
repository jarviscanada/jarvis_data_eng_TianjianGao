package ca.jrvs.apps.grep;

import java.io.IOException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepImplTest {
    static final Logger logger = LoggerFactory.getLogger(JavaGrepImplTest.class);

    @Test
    public void testGrep() throws IOException {
        JavaGrepImpl grep = new JavaGrepImpl();
        String pattern = ".*Romeo.*Juliet.*";
        grep.setRegex(pattern);
        String testPath = "/Users/gao/Projects/jarvis_data_eng_TianjianGao/core_java/grep/data/txt/";
        grep.setRootPath(testPath);
        String outFile = "grep2.txt";
        grep.setOutFile(outFile);

        grep.process();

    }

}