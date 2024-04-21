package com.eland.utils.excel.one;

import com.eland.pojo.info.excel.CellInfo;
import com.eland.pojo.info.excel.SheetInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class ExcelUtils {

    // 製作Excel
    public static XSSFWorkbook writeExcelByRow(XSSFWorkbook workbook, HashMap<String, XSSFCellStyle> styleMapping, List<SheetInfo> sheetInfoList) {
        CreationHelper creationHelper = workbook.getCreationHelper();
        for (SheetInfo sheetInfo : sheetInfoList) {
            XSSFSheet sheet = workbook.createSheet(sheetInfo.getSheetName());
            LinkedList<LinkedList<CellInfo>> cellInfoListList = sheetInfo.getCellInfoListList();
            int rowNum = 0;
            for (LinkedList<CellInfo> cellInfoList : cellInfoListList) {
                Row row = sheet.createRow(rowNum);
                int colNum = 0;
                for (CellInfo cellInfo : cellInfoList) {
                    // 第1列設置儲存格寬度
                    if (rowNum == 0) {
                        sheet.setColumnWidth(colNum, cellInfo.getWidth() * 256);
                    }
                    // 儲存格內容
                    Cell cell = row.createCell(colNum);
                    Object value = cellInfo.getValue();
                    if (value instanceof Integer) {
                        cell.setCellValue(Integer.parseInt(value.toString()));
                    } else if (value instanceof Double) {
                        cell.setCellValue(Double.parseDouble(value.toString()));
                    } else {
                        String string = value.toString();
                        string = string.length() >= 32767 ? string.substring(0, 32767) : string;
                        cell.setCellValue(string);
                    }
                    // 儲存格樣式
                    cell.setCellStyle(styleMapping.get(cellInfo.getStyle()));
                    // 儲存格連結
                    if (cellInfo.isHasUrl()) {
                        Hyperlink hyperlink = creationHelper.createHyperlink(HyperlinkType.URL);
                        hyperlink.setAddress(cellInfo.getAddress());
                        cell.setHyperlink(hyperlink);
                    }
                    colNum++;
                }
                rowNum++;
            }
        }
        return workbook;
    }

    // 放置Excel到指定路徑
    public static boolean generateExcel(XSSFWorkbook workbook, String filePath) {
        try {
            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
            return true;
        } catch (Exception e) {
            log.error("ExcelUtils GenerateExcel Error: ", e);
            return false;
        }
    }

}
