package java05;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

public class Test02 {

    public static void main(String[] args) {
        File[] roots = File.listRoots(); // [C:\, D:\, E:\, G:\]

        File dir = new File("D:\\aaa");

        File[] files = dir.listFiles();
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile() && pathname.getName().endsWith(".txt");
            }
        });
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                File f = new File(dir, name);
                return f.isFile() && f.getName().endsWith(".txt");
            }
        });

        String[] pathNames = dir.list();
        String[] pathNames = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                File file = new File(dir, name);
                return file.isFile() && name.endsWith(".txt");
            }
        });
    }

}
