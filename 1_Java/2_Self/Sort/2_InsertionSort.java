package java04;

public class InsertionSort {

    public static void main(String[] args) {
        int[] numbers = Tool.generateNumbers(10, 100);
        insertionSort(numbers);
    }

    private static void insertionSort(int[] numbers) {
        for (int idx = 1; idx < numbers.length; idx++) {
            int curVal = numbers[idx];
            int preIdx = idx - 1;
            while (preIdx >= 0 && curVal < numbers[preIdx]) {
                numbers[preIdx + 1] = numbers[preIdx];
                preIdx--;
            }
            numbers[preIdx + 1] = curVal;
            Tool.printArray(numbers);
        }
    }

}