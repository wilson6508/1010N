package java04;

public class BubbleSort {

    public static void main(String[] args) {
        int[] numbers = Tool.generateNumbers(10, 100);
        Tool.printArray(numbers);
        bubbleSort(numbers);
        Tool.printArray(numbers);
    }

    public static void bubbleSort(int[] arr) {
        int length = arr.length;
        while (length > 1) {
            length--;
            for (int i = 0; i < length; i++) {
                if (arr[i] > arr[i + 1]) {
                    int temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                }
            }
        }
    }

    private static void bubbleSort(int[] numbers) {
        boolean check = true;
        while (check) {
            check = false;
            for (int i = 0; i < numbers.length - 1; i++) {
                if (numbers[i + 1] < numbers[i]) {
                    check = true;
                    int temp = numbers[i];
                    numbers[i] = numbers[i + 1];
                    numbers[i + 1] = temp;
                }
            }
        }
    }
    
}