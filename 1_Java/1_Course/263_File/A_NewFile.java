package com.itheima.file_demo;

import java.io.File;

public class A_NewFile {

    // public File(String pathName)
    // public File(String parent, String child)
    // public File(File parent, String child)

    public static void main(String[] args) {
        String pathName = "D:\\aaa\\c.txt";
        File f1 = new File(pathName);
        System.out.println(f1);

        String parent2 = "D:\\aaa";
        String child2 = "c.txt";
        File f2 = new File(parent2, child2);
        System.out.println(f2);

        File parent3 = new File("D:\\aaa");
        String child3 = "c.txt";
        File f3 = new File(parent3, child3);
        System.out.println(f3);
    }

}
