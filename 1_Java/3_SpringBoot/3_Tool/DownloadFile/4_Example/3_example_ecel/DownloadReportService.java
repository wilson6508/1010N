package com.eland.service.insight.report;

import com.eland.pojo.dto.insight.InsightApiResponseDTO;
import com.eland.pojo.info.UserInfo;
import com.eland.pojo.info.report.GeneralReportTaskInfo;
import com.google.gson.Gson;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
public class DownloadReportService {

    @Autowired
    GeneralReportTaskService generalReportTaskService;

    public void downloadReportHtml(UserInfo userInfo, GeneralReportTaskInfo taskInfo, HttpServletResponse response) {
        String htmlContent = getHtmlContent(userInfo, taskInfo);
        boolean success = !htmlContent.equals("");
        InputStream inputStream = new ByteArrayInputStream(htmlContent.getBytes());
        downloadNormalFile(response, success, inputStream);
    }

    public void downloadReportExcel(UserInfo userInfo, GeneralReportTaskInfo taskInfo, HttpServletResponse response) {
        InputStream inputStream = getClass().getResourceAsStream("/test0406.xlsx");
        boolean success = inputStream != null;
        downloadExcelFile(response, success, inputStream);
    }

    public String getHtmlContent(UserInfo userInfo, GeneralReportTaskInfo taskInfo) {
        InsightApiResponseDTO responseDTO = generalReportTaskService.readHtml(userInfo, taskInfo);
        if (responseDTO.getResponseInfo().getErrorCode() == 0.0) {
            Gson gson = new Gson();
            GeneralReportTaskInfo temp = gson.fromJson(gson.toJson(responseDTO.getResult()), GeneralReportTaskInfo.class);
            return temp.getHtmlContent();
        }
        return "";
    }

    public void downloadNormalFile(HttpServletResponse response, boolean success, InputStream inputStream) {
        byte[] buffer = new byte[1024];
        BufferedInputStream bufferedInputStream = null;
        OutputStream outputStream = null;
        try {
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "success=" + success);
            outputStream = response.getOutputStream();
            bufferedInputStream = new BufferedInputStream(inputStream);
            while (bufferedInputStream.read(buffer) != -1) {
                outputStream.write(buffer);
            }
        } catch (Exception e) {
            response.setHeader("Content-Disposition", "success=false");
            e.printStackTrace();
        } finally {
            try {
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void downloadExcelFile(HttpServletResponse response, boolean success, InputStream inputStream) {
        XSSFWorkbook xssfWorkbook = null;
        OutputStream outputStream = null;
        try {
            xssfWorkbook = new XSSFWorkbook();
            if (inputStream != null) {
                xssfWorkbook = new XSSFWorkbook(inputStream);
            }
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "success=" + success);
            outputStream = response.getOutputStream();
            xssfWorkbook.write(outputStream);
        } catch (Exception e) {
            response.setHeader("Content-Disposition", "success=false");
            e.printStackTrace();
        } finally {
            try {
                if (xssfWorkbook != null) {
                    xssfWorkbook.close();
                }
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}