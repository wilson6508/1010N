package demo2;

import java.util.HashMap;

public class HashDemo1 {

    public static void main(String[] args) {
        // HashMap的鍵如果是自定義對象 對象要重寫equals跟hashCode
        HashMap<Student, String> hashMap = new HashMap<>();
        Student s1 = new Student("Tom", 18);
        Student s2 = new Student("Tom", 19);
        hashMap.put(s1, "LA");
        hashMap.put(s2, "DAL");
        System.out.println(hashMap);

        // 先計算key的hash值 如果該索引已經有存放鍵值對
        // 用equals比較新舊key是否相等
        // 相等: 新的鍵值對 覆蓋 舊的鍵值對
        // 不相等: 新的鍵值對 掛在 舊的鍵值對 底下 (紅黑樹)
    }

}
