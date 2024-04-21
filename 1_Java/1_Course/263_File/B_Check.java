package com.itheima.file_demo;

import java.io.File;

public class B_Check {

    public static void main(String[] args) {
        // 檔案不存在 則皆為false
        File f1 = new File("D:\\aaa\\a.txt");
        System.out.println(f1.exists());
        System.out.println(f1.isFile());
        System.out.println(f1.isDirectory());

        long length = f1.length();              // 檔案大小bytes
        System.out.println(length);             // 3
        String name = f1.getName();             // 檔案名稱
        System.out.println(name);               // a.txt
        long lastModified = f1.lastModified();  // 最後修改時間
        System.out.println(lastModified);       // 1697962374619

        File f2 = new File("CreateDelete.png");
        String absolutePath = f2.getAbsolutePath();
        String path = f2.getPath();
        System.out.println(absolutePath); // D:\code\basic-code\CreateDelete.png
        System.out.println(path);         // CreateDelete.png
    }

}
