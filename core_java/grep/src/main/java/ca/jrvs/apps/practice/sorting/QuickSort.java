package ca.jrvs.apps.practice.sorting;

public class QuickSort {

  public static void main(String[] args) {
    int[] array = {5, 4, 3, 2, 1};
    quickSort(array, 0, array.length - 1);
  }

  /**
   * Driver
   * @param array
   * @param low
   * @param high
   */
  public static void quickSort(int[] array, int low, int high) {
    if (low < high) {
      int partitionIndex = partition(array, low, high);
      quickSort(array, low, partitionIndex - 1);
      quickSort(array, partitionIndex + 1, high);
    }
  }

  private static int partition(int[] array, int low, int high) {
    int pivot = array[high];
    int i = low - 1;
    for (int j = low; j < high; j++) {
      if (array[j] < pivot) {
        i++;
//        System.out.print("1: ");
//        Util.printArray(array);
        Util.swap(array, i, j);
//        System.out.print("2: ");
//        Util.printArray(array);
      }
    }

    Util.swap(array, i + 1, high);
//    System.out.print("3: ");
//    Util.printArray(array);
    return i + 1;
  }

}
