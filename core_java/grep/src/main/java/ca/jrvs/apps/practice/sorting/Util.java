package ca.jrvs.apps.practice.sorting;

import org.jetbrains.annotations.NotNull;

public class Util {
  public static void swap(int @NotNull [] array, int i, int j) {
    int temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }

  public static void printArray(int[] array) {
    for (int j : array) {
      System.out.print(j + " ");
    }
    System.out.println();
  }
}

