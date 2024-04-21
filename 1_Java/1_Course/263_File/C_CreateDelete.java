package com.itheima.file_demo;

import java.io.File;
import java.io.IOException;

public class C_CreateDelete {

    public static void main(String[] args) throws IOException {
        File f1 = new File("D:\\aaa\\c.txt");
        // true創建成功 false創建失敗(已存在)
        // 父級路徑不存在 拋IOException
        boolean b1 = f1.createNewFile();

        File f2 = new File("D:\\aaa\\ddd");
        boolean b2 = f2.mkdir(); // 單級

        File f3 = new File("D:\\aaa\\ccc\\ggg");
        boolean b3 = f3.mkdirs(); // 多級

        File f4 = new File("D:\\aaa\\ccc");
        boolean b4 = f4.delete(); // 有內容的文件夾 返回false
    }

}
