package demo4;

import java.io.File;

public class Test2 {

    public static void main(String[] args) {
        boolean b = hasMp4(new File("D:"));
        System.out.println("b = " + b);
    }

    public static boolean hasMp4(File dir) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".mp4")) {
                return true;
            }
        }
        return false;
    }

    public static void findMp4() {
        File[] arr = File.listRoots();
        for (File dir : arr) {
            findMp4(dir);
        }
    }

    public static void findMp4(File dir) {
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                findMp4(file);
            } else {
                if (file.getName().endsWith(".mp4")) {
                    System.out.println(file);
                }
            }
        }
    }

    public static void deleteDir(File dir) {
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                deleteDir(file);
            } else {
                file.delete();
            }
        }
    }

    public static void rmDir(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    rmDir(file);
                } else {
                    file.delete();
                }
            }
        }
        dir.delete();
    }

}
