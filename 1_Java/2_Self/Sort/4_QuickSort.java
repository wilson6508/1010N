package java04;

import java.util.Random;

public class QuickSort {

    public static void main(String[] args) {
        int[] numbers = { 38,27,43,3,9,82,10,55 };
        quickSort(numbers);
    }

    private static void quickSort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private static void quickSort(int[] array, int lowIdx, int highIdx) {
        Tool.printArray(array);
        if (lowIdx >= highIdx) {
            return;
        }
        int pivotIdx = new Random().nextInt(highIdx - lowIdx) + lowIdx;
        int pivot = array[pivotIdx];
        swap(array, pivotIdx, highIdx);
        int leftPointer = partition(array, lowIdx, highIdx, pivot);
        quickSort(array, lowIdx, leftPointer - 1);
        quickSort(array, leftPointer + 1, highIdx);
    }

    private static int partition(int[] array, int lowIndex, int highIndex, int pivot) {
        int leftPointer = lowIndex;
        int rightPointer = highIndex - 1;
        while (leftPointer < rightPointer) {
            // Walk from the left until we find a number greater than the pivot, or hit the right pointer.
            while (array[leftPointer] <= pivot && leftPointer < rightPointer) {
                leftPointer++;
            }
            // Walk from the right until we find a number less than the pivot, or hit the left pointer.
            while (array[rightPointer] >= pivot && leftPointer < rightPointer) {
                rightPointer--;
            }
            swap(array, leftPointer, rightPointer);
        }
        // This is different from what the video has, and fixes an issue where the last value could potentially be out of order.
        // Thanks to viewer Wilson Barbosa for suggesting the fix!
        if(array[leftPointer] > array[highIndex]) {
            swap(array, leftPointer, highIndex);
        } else {
            leftPointer = highIndex;
        }
        return leftPointer;
    }

    private static void swap(int[] array, int idx1, int idx2) {
        int temp = array[idx1];
        array[idx1] = array[idx2];
        array[idx2] = temp;
    }

}
