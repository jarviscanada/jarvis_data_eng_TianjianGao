package ca.jrvs.apps.practice;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LambdaStreamExcTest {

  private LambdaStreamExcImpl lambdaStreamExcImpl;

  @BeforeEach
  public void setUp() {
    lambdaStreamExcImpl = new LambdaStreamExcImpl();
  }

  @Test
  public void testCreateStringStream() {
    Stream<String> stringStream = lambdaStreamExcImpl.createStringStream("hello", "world", "test");
    List<String> resultList = stringStream.toList();
    assertEquals(Arrays.asList("hello", "world", "test"), resultList);
  }

  @Test
  public void testToUpperCaseStream() {
    Stream<String> result = lambdaStreamExcImpl.toUpperCaseStream("hello", "world");
    List<String> resultList = result.toList();
    assertEquals(Arrays.asList("HELLO", "WORLD"), resultList);
  }

  @Test
  public void testFilter() {
    Stream<String> stringStream = Stream.of("apple", "banana", "cherry");
    Stream<String> result = lambdaStreamExcImpl.filter(stringStream, ".*a.*");
    List<String> resultList = result.toList();
    assertEquals(Arrays.asList("apple", "banana"), resultList);
  }

  @Test
  public void testCreateIntStream() {
    IntStream intStream = lambdaStreamExcImpl.createIntStream(new int[]{1, 2, 3});
    List<Integer> resultList = intStream.boxed().toList();
    assertEquals(Arrays.asList(1, 2, 3), resultList);
  }

  @Test
  public void testToListStream() {
    Stream<String> stream = Stream.of("apple", "banana", "cherry");
    List<String> resultList = lambdaStreamExcImpl.toList(stream);
    assertEquals(Arrays.asList("apple", "banana", "cherry"), resultList);
  }

  @Test
  public void testCreateIntStreamRange() {
    IntStream intStream = lambdaStreamExcImpl.createIntStream(0, 10);
    List<Integer> resultList = intStream.boxed().toList();
    assertEquals(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), resultList);
  }

  @Test
  public void testSquareRootIntStream() {
    IntStream intStream = lambdaStreamExcImpl.createIntStream(new int[]{1, 4, 9, 16, 25});
    List<Double> result = lambdaStreamExcImpl.squareRootIntStream(intStream).boxed().toList();
    assertEquals(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0), result);
  }

  @Test
  public void testGetLambdaPrinter() {
    Consumer<String> printer = lambdaStreamExcImpl.getLambdaPrinter("start>", "<end");
    assertNotNull(printer);
    printer.accept("test message");
  }

  @Test
  public void testFlatNestedInt() {
    Stream<List<Integer>> input = Stream.of(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6));
    List<Integer> result = lambdaStreamExcImpl.flatNestedInt(input).toList();
    assertEquals(Arrays.asList(1, 4, 9, 16, 25, 36), result);
  }
}
