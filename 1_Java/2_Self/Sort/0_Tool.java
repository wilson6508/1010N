package java04;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Tool {

    public static int[] generateNumbers(int length, int range) {
        Random rand = new Random();
        int[] numbers = new int[length];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = rand.nextInt(range); // 0 ~ range-1
        }
        return numbers;
    }

    public static void printArray(int[] numbers) {
        List<Integer> list = Arrays.stream(numbers).boxed().collect(Collectors.toList());
        System.out.println(list);
    }

    public static void printArray(int[] numbers) {
        System.out.println(Arrays.toString(numbers));
    }

}