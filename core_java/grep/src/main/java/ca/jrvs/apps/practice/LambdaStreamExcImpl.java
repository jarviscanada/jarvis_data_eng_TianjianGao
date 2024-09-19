package ca.jrvs.apps.practice;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LambdaStreamExcImpl implements LambdaStreamExc {

    static final Logger logger = LoggerFactory.getLogger(LambdaStreamExc.class);

    private String regex;
    private String rootPath;
    private String outFile;

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }

    public String getOutFile() {
        return outFile;
    }

    public static void main(String[] args) throws IllegalArgumentException {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        BasicConfigurator.configure();

        LambdaStreamExcImpl exc = new LambdaStreamExcImpl();
        exc.setRegex(args[0]);
        exc.setRootPath(args[1]);
        exc.setOutFile(args[2]);

        try {
            exc.process();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void process() {
    }

    /**
     * Create a String Stream from array
     * <p>
     * note: arbitrary number of value will be stored in an array
     *
     * @param strings
     * @return
     */
    @Override
    public Stream<String> createStringStream(String... strings) {
        return Arrays.stream(strings);
    }

    /**
     * Convert all strings to uppercase This method uses createStringStream
     *
     * @param strings
     * @return
     */
    @Override
    public Stream<String> toUpperCaseStream(String... strings) {
        return createStringStream(strings).map(String::toUpperCase);
    }

    /**
     * filter strings that contains the pattern e.g. filter(stringStream, "a") will
     * return another
     * stream which no element contains a
     *
     * @param stringStream
     * @param pattern
     * @return
     */
    @Override
    public Stream<String> filter(Stream<String> stringStream, String pattern) {
        return stringStream.filter(p -> p.matches(pattern));
    }

    /**
     * Create an intStream from an arr[]
     *
     * @param arr
     * @return
     */
    @Override
    public IntStream createIntStream(int[] arr) {
        return IntStream.of(arr);
    }

    /**
     * Convert a stream to list
     *
     * @param stream
     * @return
     */
    @Override
    public <E> List<E> toList(Stream<E> stream) {
        return stream.collect(Collectors.toList());
    }

    /**
     * Convert an intStream to list
     *
     * @param intStream
     * @return
     */
    @Override
    public List<Integer> toList(IntStream intStream) {
        return intStream.boxed().collect(Collectors.toList());
    }

    /**
     * Create an InStream range from start to end inclusive
     *
     * @param start
     * @param end
     * @return
     */
    @Override
    public IntStream createIntStream(int start, int end) {
        return IntStream.range(start, end);
    }

    /**
     * Convert an intStream to a doubleStream and compute square root of each
     * element
     *
     * @param intStream
     * @return
     */
    @Override
    public DoubleStream squareRootIntStream(IntStream intStream) {
        return intStream.mapToDouble(Math::sqrt);
    }

    /**
     * filter all even number and return odd numbers from an intStream
     *
     * @param intStream
     * @return
     */
    @Override
    public IntStream getOdd(IntStream intStream) {
        return intStream.filter(i -> i % 2 == 1);
    }

    /**
     * Return a lambda function that prints a message with a prefix and suffix
     *
     * This lambda can be useful to format logs
     * <p>
     * references: functional interface <a href="http://bit.ly/2pTXRwM">...</a> &
     * <a href="http://bit.ly/33onFig">...</a> lambda syntax
     * <p>
     * usage: LambdaStreamExc lse = new LambdaStreamImp(); Consumer<String> printer
     * =
     * lse.getLambdaPrinter("start>", "<end"); printer.accept("Message body");
     * <p>
     * sout: start>Message body<end
     *
     * @param prefix prefix str
     * @param suffix suffix str
     * @return
     */
    @Override
    public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
        return (message) -> System.out.println(prefix + message + suffix);
    }

    /**
     * Print each message with a given printer Please use `getLambdaPrinter` method
     * <p>
     * e.g. String[] messages = {"a","b","c"}; lse.printMessages(messages,
     * lse.getLambdaPrinter("msg:", "!") );
     * <p>
     * sout msg:a! msg:b! msg:c!
     *
     * @param messages
     * @param printer
     */
    @Override
    public void printMessages(String[] messages, Consumer<String> printer) {
        Arrays.stream(messages).forEach(printer);
    }

    /**
     * Square each number from the input. Create two solutions and compare
     * difference using
     * flatMap
     *
     * @param ints
     * @return
     */
    @Override
    public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
        // return ints.flatMap(Collection::stream).map(i -> i * i);
        return ints.flatMap(list -> list.stream().map(i -> i * i));
    }
}