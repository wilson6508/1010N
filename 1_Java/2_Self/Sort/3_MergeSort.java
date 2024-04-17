import java.util.Arrays;

public class TestMain {
    public static void main(String[] args) {
        int[] numbers = {38, 27, 43, 3, 9, 82, 10, 55, 1};
        mergeSort(numbers);
        printArr(numbers);
    }
    public static void mergeSort(int[] inputArr) {
        if (inputArr.length < 2) {
            return;
        }
        int midIdx = inputArr.length / 2;
        int[] leftArr = Arrays.copyOfRange(inputArr, 0, midIdx);
        int[] rightArr = Arrays.copyOfRange(inputArr, midIdx, inputArr.length);
        mergeSort(leftArr);
        mergeSort(rightArr);
        merge(leftArr, rightArr, inputArr);
    }
    public static void merge(int[] leftArr, int[] rightHalf, int[] inputArr) {
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < leftArr.length && j < rightHalf.length) {
            if (leftArr[i] <= rightHalf[j]) {
                inputArr[k] = leftArr[i];
                i++;
            } else {
                inputArr[k] = rightHalf[j];
                j++;
            }
            k++;
        }
        while (i < leftArr.length) {
            inputArr[k] = leftArr[i];
            i++;
            k++;
        }
        while (j < rightHalf.length) {
            inputArr[k] = rightHalf[j];
            j++;
            k++;
        }
    }
    public static void printArr(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }
}
--------------------------------------------------------------------------------------
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MergeSort {

    public static void main(String[] args) {
        int[] numbers = { 38,27,43,3,9,82,10,55 };
        mergeSort(numbers);
    }

    private static void mergeSort(int[] inputArr) {
        int arrayLength = inputArr.length;
        if (arrayLength < 2) {
            return;
        }
        // 拆分成左右陣列
        int midIdx = arrayLength / 2;
        int[] leftHalf = new int[midIdx];
        int[] rightHalf = new int[arrayLength - midIdx];
        // 賦值給左陣列
        for (int i = 0; i < midIdx; i++) {
            leftHalf[i] = inputArr[i];
        }
        // 賦值給右陣列
        for (int i = midIdx; i < inputArr.length; i++) {
            rightHalf[i - midIdx] = inputArr[i];
        }
        mergeSort(leftHalf);
        mergeSort(rightHalf);
        merge(inputArr, leftHalf, rightHalf);
    }

    /**
     * @param inputArr 合併後且經過排序的陣列
     * @param leftHalf 左半部陣列
     * @param rightHalf 右半部陣列
     */
    private static void merge(int[] inputArr, int[] leftHalf, int[] rightHalf) {
        Tool.printArray(inputArr);
        int leftLength = leftHalf.length;
        int rightLength = rightHalf.length;
        int i = 0, j = 0, k = 0;
        // 左右陣列比較 > 賦值給inputArr
        while (i < leftLength && j < rightLength) {
            if (leftHalf[i] <= rightHalf[j]) {
                inputArr[k] = leftHalf[i];
                i++;
            } else {
                inputArr[k] = rightHalf[j];
                j++;
            }
            k++;
        }
        // 若左半部陣列還有剩餘的值 尚未賦值給inputArr
        while (i < leftLength) {
            inputArr[k] = leftHalf[i];
            i++;
            k++;
        }
        // 若右半部陣列還有剩餘的值 尚未賦值給inputArr
        while (j < rightLength) {
            inputArr[k] = rightHalf[j];
            j++;
            k++;
        }
    }

}
--------------------------------------------------------------------------------------
