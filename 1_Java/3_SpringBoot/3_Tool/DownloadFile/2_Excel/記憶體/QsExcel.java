package com.eland.controller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;

// 使用XSSFWorkbook與SXSSFWorkbook在處理Excel合併
// https://www.twblogs.net/a/5d4353a6bd9eee5327fb2bb2

// poi的3.8版本使用SXSSFWorkbook的簡單測試
// https://www.twblogs.net/a/5cfe3335bd9eee14644eaa14

@RestController
public class HelloWorld {

/*
Total Memory: 371 MB
Free Memory: 301 MB
Used Memory: 70 MB

Total Memory: 371 MB
Free Memory: 182 MB
Used Memory: 189 MB

Total Memory: 398 MB
Free Memory: 260 MB
Used Memory: 138 MB

Total Memory: 403 MB
Free Memory: 346 MB
Used Memory: 56 MB

Total Memory: 403 MB
Free Memory: 223 MB
Used Memory: 179 MB

Total Memory: 406 MB
Free Memory: 309 MB
Used Memory: 96 MB

process 30000 spent time:5355
*/
    @GetMapping("/quickExcel")
    public String quickExcel(@RequestParam("rowsNum") int lastRow) {
        long startTime = System.currentTimeMillis();
        SXSSFWorkbook wb = null;
        try {
            wb = new SXSSFWorkbook();
            wb.setCompressTempFiles(true);
            Sheet sh = wb.createSheet();
            for (int rowNum = 0; rowNum < lastRow; rowNum++) {
                Row row = sh.createRow(rowNum);
                for (int colNum = 0; colNum < 30; colNum++) {
                    Cell cell = row.createCell(colNum);
                    cell.setCellValue(Math.random());
                }
                if (rowNum % 5000 == 0) {
                    Runtime runtime = Runtime.getRuntime();
                    long totalMemoryBytes = runtime.totalMemory();
                    long freeMemoryBytes = runtime.freeMemory();
                    long usedMemoryBytes = totalMemoryBytes - freeMemoryBytes;
                    long totalMemoryMB = totalMemoryBytes / (1024 * 1024);
                    long freeMemoryMB = freeMemoryBytes / (1024 * 1024);
                    long usedMemoryMB = usedMemoryBytes / (1024 * 1024);
                    System.out.println("Total Memory: " + totalMemoryMB + " MB");
                    System.out.println("Free Memory: " + freeMemoryMB + " MB");
                    System.out.println("Used Memory: " + usedMemoryMB + " MB");
                    System.out.println();
                }
            }
            String filePath = String.format("C:/Users/wilsonhuang/Desktop/testSH/%s.xlsx", "quick");
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            wb.write(fileOutputStream);
            wb.close();
            fileOutputStream.close();
            long endTime = System.currentTimeMillis();
            System.out.println("process " + lastRow + " spent time:" + (endTime - startTime));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (wb != null) {
                wb.dispose();// 刪除臨時文件，很重要，否則磁盤可能會被寫滿
            }
        }
        return "done";
    }


/*
Total Memory: 406 MB
Free Memory: 175 MB
Used Memory: 230 MB

Total Memory: 424 MB
Free Memory: 201 MB
Used Memory: 222 MB

Total Memory: 650 MB
Free Memory: 344 MB
Used Memory: 305 MB

Total Memory: 719 MB
Free Memory: 322 MB
Used Memory: 396 MB

Total Memory: 753 MB
Free Memory: 275 MB
Used Memory: 478 MB

Total Memory: 1242 MB
Free Memory: 682 MB
Used Memory: 560 MB

process 30000 spent time:13282
*/
    @GetMapping("/slowExcel")
    public String slowExcel(@RequestParam("rowsNum") int lastRow) {
        long startTime = System.currentTimeMillis();
        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            Sheet sh = wb.createSheet();
            for (int rowNum = 0; rowNum < lastRow; rowNum++) {
                Row row = sh.createRow(rowNum);
                for (int colNum = 0; colNum < 30; colNum++) {
                    Cell cell = row.createCell(colNum);
                    cell.setCellValue(Math.random());
                }
                if (rowNum % 5000 == 0) {
                    Runtime runtime = Runtime.getRuntime();
                    long totalMemoryBytes = runtime.totalMemory();
                    long freeMemoryBytes = runtime.freeMemory();
                    long usedMemoryBytes = totalMemoryBytes - freeMemoryBytes;
                    long totalMemoryMB = totalMemoryBytes / (1024 * 1024);
                    long freeMemoryMB = freeMemoryBytes / (1024 * 1024);
                    long usedMemoryMB = usedMemoryBytes / (1024 * 1024);
                    System.out.println("Total Memory: " + totalMemoryMB + " MB");
                    System.out.println("Free Memory: " + freeMemoryMB + " MB");
                    System.out.println("Used Memory: " + usedMemoryMB + " MB");
                    System.out.println();
                }
            }
            String filePath = String.format("C:/Users/wilsonhuang/Desktop/testSH/%s.xlsx", "slow");
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            wb.write(fileOutputStream);
            wb.close();
            fileOutputStream.close();
            long endTime = System.currentTimeMillis();
            System.out.println("process " + lastRow + " spent time:" + (endTime - startTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "done";
    }

}