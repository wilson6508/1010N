package com.eland.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileIoUtil {

    private FileIoUtil() {
    }

    // 建立資料夾
    public static void mkDir(String dirPath) {
        File dir = new File(dirPath);
        if (dir.exists()) {
            rmDir(dir);
        }
        dir.mkdir();
    }

    // 刪除資料夾
    public static void rmDir(File dir) {
        try {
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 將excel檔壓縮成zip檔
    public static void compressFile(String sourceFile, String destPath) {
        File[] sourceFiles = {new File(sourceFile)};
        File destZip = new File(destPath);
        try {
            compressFilesToZip(sourceFiles, destZip);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 將資料夾壓縮成zip檔
    public static void compressDir(String dirPath, String destPath) {
        File[] sourceFiles = new File(dirPath).listFiles();
        File destZip = new File(destPath);
        if (sourceFiles != null) {
            try {
                compressFilesToZip(sourceFiles, destZip);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 將files壓縮成zip檔
    public static void compressFilesToZip(File[] sourceFiles, File destZip) throws IOException {
        byte[] bytes = new byte[1024];
        ZipOutputStream zipOutputStream = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(destZip);
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            for (File sourceFile : sourceFiles) {
                ZipEntry zipEntry = new ZipEntry(sourceFile.getName());
                zipOutputStream.putNextEntry(zipEntry); // 命名
                int length;
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                while ((length = fileInputStream.read(bytes)) > 0) {
                    zipOutputStream.write(bytes, 0, length);
                }
                zipOutputStream.closeEntry();
                fileInputStream.close();
            }
        } finally {
            if (zipOutputStream != null) {
                try {
                    zipOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
