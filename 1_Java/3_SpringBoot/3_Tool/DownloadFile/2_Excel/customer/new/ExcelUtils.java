package com.eland.utils.excel.one;

import com.eland.pojo.info.excel.CellInfo;
import com.eland.pojo.info.excel.SheetInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;

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
            Sheet sheet = workbook.getSheet(sheetInfo.getSheetName());
            if (sheet == null) {
                sheet = workbook.createSheet(sheetInfo.getSheetName());
            }
            LinkedList<LinkedList<CellInfo>> cellInfoListList = sheetInfo.getCellInfoListList();
            int rowIdx = sheetInfo.getStartRow();
            for (LinkedList<CellInfo> cellInfoList : cellInfoListList) {
                Row row = sheet.getRow(rowIdx);
                if (row == null) {
                    row = sheet.createRow(rowIdx);
                }
                int colIdx = sheetInfo.getStartCol();
                for (CellInfo cellInfo : cellInfoList) {
                    // 第1列設置儲存格寬度
                    if (rowIdx == 0 && cellInfo.getWidth() > 0) {
                        sheet.setColumnWidth(colIdx, cellInfo.getWidth() * 256);
                    }
                    Cell cell = row.getCell(colIdx);
                    if (cell == null) {
                        cell = row.createCell(colIdx);
                    }
                    // 儲存格內容
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
                    if (!CollectionUtils.isEmpty(styleMapping)) {
                        cell.setCellStyle(styleMapping.get(cellInfo.getStyle()));
                    }
                    // 儲存格連結
                    if (cellInfo.isHasUrl()) {
                        Hyperlink hyperlink = creationHelper.createHyperlink(HyperlinkType.URL);
                        hyperlink.setAddress(cellInfo.getAddress());
                        cell.setHyperlink(hyperlink);
                    }
                    colIdx++;
                }
                rowIdx++;
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
