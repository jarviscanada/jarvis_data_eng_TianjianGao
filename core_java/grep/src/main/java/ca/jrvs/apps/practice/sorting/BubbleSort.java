package ca.jrvs.apps.practice.sorting;

/**
 * useful when array is mostly sorted
 */
public class BubbleSort {

  public static void main(String[] args) {
    int[] original = {5, 4, 3, 2, 1};
    int[] array = original.clone();

    bubbleSort_lr(array);

    System.out.println();

    array = original.clone();

    bubbleSort_rl(array);
  }

  public static void bubbleSort_lr(int[] array) {
    for (int i = 0; i < array.length - 1; i++) {
      for (int j = 0; j < array.length - i - 1; j++) {
        if (array[j] > array[j + 1]) {
          Util.swap(array, j, j + 1);
        }
        Util.printArray(array);
      }
    }
  }

  public static void bubbleSort_rl(int[] array) {
    for (int i = 0; i < array.length; i++) {
      for (int j = array.length - 1; j > i; j--) {
        if (array[j] < array[j - 1]) {
          Util.swap(array, j, j - 1);
        }
        Util.printArray(array);
      }
    }
  }
}
