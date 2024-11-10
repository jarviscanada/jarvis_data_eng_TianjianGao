package ca.jrvs.apps.stockquote.practice;

public class SimpleCalculatorImpl implements SimpleCalculator {
  public SimpleCalculatorImpl() {
  }

  public int add(int a, int b) {
    return a + b;
  }

  public int subtract(int a, int b) {
    return a - b;
  }

  public int multiply(int a, int b) {
    return a * b;
  }

  public double divide(int a, int b) {
    return (double)(a / b);
  }

  public int power(int a, int b) {
    return (int)Math.pow((double)a, (double)b);
  }

  public double abs(double a) {
    return Math.abs(a);
  }
}