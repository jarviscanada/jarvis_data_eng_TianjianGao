package ca.jrvs.apps.practice.sorting;

public class InsertionSort {
//  public static <T extends Comparable<T>> void sort(T[] array) {
//    for (int i = 1; i < array.length; i++) {
//      T current = array[i];
//      int j = i - 1;
//      while (array[j].compareTo(current) < 0) {
//        array[j + 1] = array[j];
//
//      }
//    }
//  }

  public static void main(String[] args) {
    int[] arr = { 9, 8, 7, 6, 5, 4, 3, 2, 1 };
    insertionSort(arr);

  }

  public static void insertionSort(int[] arr) {
    for (int i = 1; i < arr.length; i++) {
      int key = arr[i];
      int j = i - 1;
      while (j >= 0 && arr[j] > key) {
        arr[j + 1] = arr[j];
        Util.printArray(arr);
        j--;
      }
      arr[j + 1] = key;
      Util.printArray(arr);
    }
  }
}
