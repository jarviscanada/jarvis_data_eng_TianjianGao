package ca.jrvs.apps.jdbc.practice;

public class NotSoSimpleCalculatorImpl implements NotSoSimpleCalculator {
  private SimpleCalculator calculator;

  public NotSoSimpleCalculatorImpl(SimpleCalculator calculator) {
    this.calculator = calculator;
  }

  public int power(int x, int y) {
    return this.calculator.power(x, y);
  }

  public int abs(int x) {
    return this.calculator.multiply(x, -1);
  }

  public double sqrt(int x) {
    return Math.sqrt((double)this.calculator.multiply(x, -1));
  }
}