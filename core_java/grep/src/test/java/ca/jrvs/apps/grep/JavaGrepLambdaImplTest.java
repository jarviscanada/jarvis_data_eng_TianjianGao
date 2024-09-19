package ca.jrvs.apps.grep;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class JavaGrepLambdaImplTest {
    // static final Logger logger =
    // LoggerFactory.getLogger(JavaGrepLambdaImpl.class);
    private JavaGrepLambdaImpl grep;

    @BeforeEach
    public void setUp() {
        grep = new JavaGrepLambdaImpl();
    }

    @Test
    void testGrep() throws IOException {
        String pattern = ".*Romeo.*Juliet.*";
        grep.setRegex(pattern);
        String testPath = "/Users/gao/Projects/jarvis_data_eng_TianjianGao/core_java/grep/data/txt/";
        grep.setRootPath(testPath);
        String outFile = "grep_lambda.txt";
        grep.setOutFile(outFile);
        grep.process();

    }

}