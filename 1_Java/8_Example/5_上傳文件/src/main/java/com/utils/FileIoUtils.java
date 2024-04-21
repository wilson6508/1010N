package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileIoUtils {

    public boolean createDir(String dirName) {
        String parent = "D:/";
        File file = new File(parent, dirName);
        return file.mkdir();
    }

    public void compressFile(File destZip, File sourceFile) {
        File[] sourceFiles = {sourceFile};
        addFileToZip(destZip, sourceFiles);
    }

    public void compressDir(File destZip, File sourceDir) {
        File[] sourceFiles = sourceDir.listFiles();
        addFileToZip(destZip, sourceFiles);
    }

    public void addFileToZip(File destZip, File[] sourceFiles) {
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
        } catch (Exception e) {
            System.out.println("FileIoUtils compressFileToZip error. The file name is " + destZip.getName());
            e.printStackTrace();
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
