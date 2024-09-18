package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JavaGrepImpl implements JavaGrep {

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args) throws IOException, IllegalArgumentException {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        BasicConfigurator.configure();

        JavaGrepImpl javaGrepImpl = new JavaGrepImpl();
        javaGrepImpl.setRegex(args[0]);
        javaGrepImpl.setRootPath(args[1]);
        javaGrepImpl.setOutFile(args[2]);

        try {
            javaGrepImpl.process();
        } catch (IOException e) {
            javaGrepImpl.logger.error(e.getMessage());
        }
    }

    /**
     *
     * @throws IOException
     */
    @Override
    public void process() throws IOException {
        // pseudocode
        // matchedLines = []
        // for file in listFilesRecursively(rootDir)
        //   for line in readLines(file)
        //      if containsPattern(line)
        //        matchedLines.add(line)
        // writeToFile(matchedLines)

        List<String> matchedLines = new ArrayList<>();
        for (File file : listFiles(getRootPath())) {
            for (String line : readLines(file)) {
                if (containsPattern(line)) {
                    matchedLines.add(line);
                }
            }
        }

        writeToFile(matchedLines);
    }

    /**
     * Returns a list of all files in the specified root directory
     *
     * @param rootDir the root directory to recursively fetch all files from
     * @return a list of files in rootDir
     */
    @Override
    public List<File> listFiles(String rootDir) {
        File dir = new File(rootDir);
        List<File> files = new ArrayList<>();

        if (dir.isDirectory()) {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                if (file.isFile()) {
                    files.add(file);
                    System.out.println("Added file: " + file.getAbsolutePath());
                }
            }
        }

        return files;
    }

    /**
     * Returns all lines in the specified input file
     *
     * @param inputFile file to be read
     * @return a list of lines from inputFile
     */
    @Override
    public List<String> readLines(File inputFile) throws IOException {
        Path path = inputFile.toPath();
        return Files.readAllLines(path);
    }

    /**
     *
     * @param line input string
     * @return
     */
    @Override
    public boolean containsPattern(String line) {
        return Pattern.compile(regex).matcher(line).matches();
    }

    /**
     *
     * @param lines matched line
     * @throws IOException
     */
    @Override
    public void writeToFile(List<String> lines) throws IOException {
        try {
            Files.write(Paths.get(outFile), lines);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String getRootPath() {
        return this.rootPath;
    }

    /**
     *
     * @param rootPath
     */
    @Override
    public void setRootPath(String rootPath) throws IllegalArgumentException {
        if (isValidRootPath(rootPath)) {
            this.rootPath = rootPath;
        } else {
            throw new IllegalArgumentException("Invalid root path: " + rootPath);
        }
    }

    /**
     *
     * @param rootPath
     * @return
     */
    public static boolean isValidRootPath(String rootPath) {
        Path path = Paths.get(rootPath);
        return Files.exists(path) && Files.isDirectory(path);
    }

    /**
     *
     * @return
     */
    @Override
    public String getRegex() {
        return this.regex;
    }

    /**
     *
     * @param regex
     */
    @Override
    public void setRegex(String regex) throws IllegalArgumentException {
        if (isValidRegex(regex)) {
            this.regex = regex;
        } else {
            throw new IllegalArgumentException("Invalid regex: " + regex);
        }
    }

    /**
     *
     * @param regex
     * @return
     */
    public static boolean isValidRegex(String regex) {
        try {
            Pattern.compile(regex);
            return true;
        } catch (PatternSyntaxException e) {
            return false;
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String getOutFile() {
        return this.outFile;
    }

    /**
     *
     * @param outFile
     */
    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }

}
