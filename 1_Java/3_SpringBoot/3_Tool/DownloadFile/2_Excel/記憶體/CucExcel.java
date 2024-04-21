package com.eland.controller;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
public class HelloWorld {

    @GetMapping("createExcel")
    public String createExcel(@RequestParam("fileName") String fileName) {
        try {
            Workbook workbook = new XSSFWorkbook();
            workbook.createSheet("Sheet1");
            String filePath = String.format("C:/Users/wilsonhuang/Desktop/testSH/%s.xlsx", fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            workbook.write(fileOutputStream);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "done";
    }

    @GetMapping("/updateExcel")
    public String updateExcel(@RequestParam("row") int row, @RequestParam("col") int col, @RequestParam("str") String str) {
        String filePath = "C:/Users/wilsonhuang/Desktop/testSH/test.xlsx";
        FileOutputStream fileOutputStream = null;
        Workbook workbook = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fileInputStream);
            fileInputStream.close();

            Sheet sheet = workbook.getSheetAt(0);
            Row excelRow = sheet.getRow(row);
            if (excelRow == null) {
                excelRow = sheet.createRow(row);
            }
            Cell cell = excelRow.createCell(col);
            cell.setCellValue(str);

            fileOutputStream = new FileOutputStream(filePath);
            workbook.write(fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "done";
    }

    @GetMapping("/coverExcel")
    public String coverExcel(@RequestParam("row") int row, @RequestParam("col") int col, @RequestParam("str") String str) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("工作表1");
        sheet.createRow(row).createCell(col).setCellValue(str);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        workbook.close();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        FileOutputStream fileOutputStream = new FileOutputStream("C:/Users/wilsonhuang/Desktop/testSH/test.xlsx");
        IOUtils.copy(byteArrayInputStream, fileOutputStream);

        byteArrayOutputStream.close();
        byteArrayInputStream.close();
        fileOutputStream.close();
        return "done";
    }

}
