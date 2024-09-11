package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepLambdaImpl implements JavaGrep{

  private static final Logger logger = LoggerFactory.getLogger(JavaGrepLambdaImpl.class);

  private String regex;
  private String rootPath;
  private String outFile;

  public static void main(String[] args) throws IOException, IllegalArgumentException {
    if (args.length != 3) {
      IllegalArgumentException iae = new IllegalArgumentException("To run the app, provide 3 arguments");
      logger.error(iae.getMessage());
      throw iae;
    }

    JavaGrepLambdaImpl app = new JavaGrepLambdaImpl();
    app.setRegex(args[0]);
    app.setRootPath(args[1]);
    app.setOutFile(args[2]);

    app.process();
  }

  /**
   * Implementation of Grep pattern matching using lambda stream APIs
   * Stream all files in rootPath
   * Try to process each line in the file streams
   *
   * @throws IOException ioe
   */
  @Override
  public void process() throws IOException {
    List<String> matches = new ArrayList<>();
    Stream<File> fileStream = listFiles(rootPath).stream();
    fileStream.forEach(file -> {
      try (Stream<String> lineStream = readLines(file).stream()) {
        lineStream.forEach(line -> {
          if (containsPattern(line)) {
            matches.add(line);
          }
        });
      } catch (IOException ioe) {
        logger.error("process{}", ioe.getMessage());
      }
    });
    writeToFile(matches);
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
   * Read a file and return all the lines
   * <p>
   * Explain FileReader, BufferedReader, and character encoding
   *
   * @param inputFile file to be read
   * @return lines
   * @throws IllegalArgumentException if a given inputFile is not a file
   */
  @Override
  public List<String> readLines(File inputFile) throws IllegalArgumentException, IOException {
    if (!inputFile.exists()) {
      IllegalArgumentException iae = new IllegalArgumentException("Input file " + inputFile + " does not exist");
      logger.error(iae.getMessage());
    }
    List<String> lines = new ArrayList<>();
    try(Stream<String> stream = Files.lines(inputFile.toPath())) {
      stream.forEach(lines::add);
    } catch (IOException | SecurityException ioe) {
      logger.error("readLines{}", ioe.getMessage());
    }
    return lines;
  }

  /**
   * check if a line contains the regex pattern (passed by user)
   *
   * @param line input string
   * @return true if there is match
   */
  @Override
  public boolean containsPattern(String line) {
    return Pattern.matches(regex, line);
  }

  /**
   * Write lines to a file
   * <p>
   * Explore: FileOutputStream, OutputStreamWriter, and BufferedWriter
   *
   * @param lines matched line
   * @throws IOException if write failed
   */
  @Override
  public void writeToFile(List<String> lines) throws IOException {
    try {
      Files.write(Paths.get(this.outFile), lines);
    } catch (IOException e) {
      logger.error(e.getMessage());
    }

  }

  /**
   * @return rootPath
   */
  @Override
  public String getRootPath() {
    return this.rootPath;
  }

  /**
   * @param rootPath String
   */
  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  /**
   * @return Regex pattern
   */
  @Override
  public String getRegex() {
    return this.regex;
  }

  /**
   * @param regex regex
   */
  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  /**
   * @return outFile
   */
  @Override
  public String getOutFile() {
    return this.outFile;
  }

  /**
   * @param outFile outFile
   */
  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }
}
