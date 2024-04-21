package com.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TxtTool {

    public static String readTextFile(String filePath) {
        File file = new File(filePath);
        StringBuilder stringBuilder = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return null;
        }
    }

}
