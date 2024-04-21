package demo3;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class StringDemo {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("AAA", "BBB", "CCC");
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        for (String s : list) {
            stringJoiner.add(s);
        }
        System.out.println(list);
        System.out.println(stringJoiner);
    }

}
