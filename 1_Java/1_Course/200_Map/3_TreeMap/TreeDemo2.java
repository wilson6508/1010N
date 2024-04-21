package demo3;

import java.util.TreeMap;

public class TreeDemo2 {

    // key: Student
    // value: 籍貫
    // 依照學生年齡升序 年齡相同 名字升序

    public static void main(String[] args) {
        TreeMap<Student, String> treeMap = new TreeMap<>();
        Student s1 = new Student("Bob", 3);
        Student s2 = new Student("Amy", 3);
        Student s3 = new Student("Tom", 1);
        treeMap.put(s1, "A");
        treeMap.put(s2, "A");
        treeMap.put(s3, "A");
        System.out.println(treeMap);
    }

}
