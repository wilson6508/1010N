package demo1;

import java.util.Arrays;

public class FunctionDemo1 {

    public static void main(String[] args) {

        Integer[] arr = {3, 5, 4, 1, 6, 2};

//        匿名內部類
//        Arrays.sort(arr, new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return o2 - o1;
//            }
//        });

//        Comparator是FunctionalInterface
//        Arrays.sort(arr, (Integer o1, Integer o2) -> {
//            return o2 - o1;
//        });

//        參數類型可省
//        參數只有一個 小括號可省
//        方法體只有一行 大括號 return 分號可省
//        Arrays.sort(arr, (o1, o2) -> o2 - o1);

        Arrays.sort(arr, FunctionDemo1::subtraction);
        System.out.println(Arrays.toString(arr));

    }

    public static int subtraction(int num1, int num2) {
        return num2 - num1;
    }

}
