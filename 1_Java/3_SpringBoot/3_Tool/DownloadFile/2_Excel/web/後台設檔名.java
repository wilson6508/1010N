package com.controller;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
public class ApiResponse {

    @RequestMapping(value = "/fileDownloadResponse", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<Object> fileDownloadResponseBean() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream = getClass().getResourceAsStream("/test0406.xlsx");
        if (inputStream != null) {
            try {
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
                xssfWorkbook.write(outputStream);
                xssfWorkbook.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(outputStream.toByteArray());
        InputStreamResource inputStreamResource = new InputStreamResource(byteArrayInputStream);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment;filename=123.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(inputStreamResource);

    }

}