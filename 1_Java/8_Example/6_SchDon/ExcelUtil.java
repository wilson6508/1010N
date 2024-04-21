package com.eland.util;

import com.eland.pojo.info.CellInfo;
import com.eland.pojo.info.ExcelStyleInfo;
import com.eland.pojo.info.SheetInfo;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.util.CollectionUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ExcelUtil {

    // 網址正則
    public static final String URL_REGEX = "^(?:http(s)?:\\/\\/)?[\\w.-]+(?:\\.[\\w\\.-]+)+[\\w\\-\\._~:/?#\\[\\]@!\\$&'\\(\\)\\*\\+,;=.]+$";

    // 千分位
    public static final String TH_INTEGER = "#,##0";
    public static final String TH_DOUBLE_1 = "#,##0.0";
    public static final String TH_DOUBLE_2 = "#,##0.00";

    // 字體
    public static final String FONT_NAME_ARIAL = "Arial";
    public static final String FONT_NAME_PMINGLIU = "新細明體";
    public static final String FONT_NAME_MICROSOFT_JHENGHEI = "微軟正黑體";

    // 顏色
    public static final short COLOR_BLACK = IndexedColors.BLACK.getIndex();
    public static final short COLOR_WHITE = IndexedColors.WHITE.getIndex();
    public static final short COLOR_YELLOW = IndexedColors.YELLOW.getIndex();
    public static final short COLOR_BLUE = IndexedColors.BLUE.getIndex();

    // 填滿
    public static final FillPatternType FILL_NO = FillPatternType.NO_FILL;
    public static final FillPatternType FILL_SOLID = FillPatternType.SOLID_FOREGROUND;

    // 垂直
    public static final VerticalAlignment VA_TOP = VerticalAlignment.TOP;
    public static final VerticalAlignment VA_CENTER = VerticalAlignment.CENTER;
    public static final VerticalAlignment VA_BOTTOM = VerticalAlignment.BOTTOM;

    // 水平
    public static final HorizontalAlignment HA_LEFT = HorizontalAlignment.LEFT;
    public static final HorizontalAlignment HA_RIGHT = HorizontalAlignment.RIGHT;
    public static final HorizontalAlignment HA_CENTER = HorizontalAlignment.CENTER;
    public static final HorizontalAlignment HA_GENERAL = HorizontalAlignment.GENERAL;

    // 框線
    public static final BorderStyle BORDER_NONE = BorderStyle.NONE;
    public static final BorderStyle BORDER_THIN = BorderStyle.THIN;

    private ExcelUtil() {}

    // 取得SXSSFWorkbook物件
    public static SXSSFWorkbook getSXSSFWorkbook() {
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();
        sxssfWorkbook.setCompressTempFiles(true);
        return sxssfWorkbook;
    }

    // 建立excel
    public static void createExcel(Workbook workbook, String filePath) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(filePath);
            workbook.write(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null) {
                    if (workbook instanceof SXSSFWorkbook) {
                        ((SXSSFWorkbook) workbook).dispose(); // 刪除臨時文件 否則磁盤可能會被寫滿
                    }
                    workbook.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 處理1個Sheet
    public static void handleSheet(Workbook workbook, SheetInfo sheetInfo) {
        CreationHelper creationHelper = workbook.getCreationHelper();
        Map<String, CellStyle> styleMapping = sheetInfo.getStyleMapping();
        // excel 分頁名稱排除字元
        String sheetName = sheetInfo.getSheetName();
        String[] excelRule = {":", "\\", "/", "?", "*", "[", "]"};
        for (String str : excelRule) {
            sheetName = sheetName.replace(str, "");
        }
        // 處理分頁
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            sheet = workbook.createSheet(sheetName);
        }
        List<List<CellInfo>> sheetData = sheetInfo.getSheetData();
        if (CollectionUtils.isEmpty(sheetData)) {
            return;
        }
        int rowIdx = sheetInfo.getStartRow();
        for (List<CellInfo> rowData : sheetData) {
            Row row = sheet.createRow(rowIdx);
            int colIdx = 0;
            for (CellInfo colData : rowData) {
                if (colData != null) {
                    Cell cell = row.createCell(colIdx);
                    handleCellVal(cell, colData);
                    handleWidth(sheet, colIdx, colData);
                    handleHeight(row, colData);
                    if (rowIdx < 65531) { // hyperlinks上限
                        handleUrl(cell, colData, creationHelper);
                    }
                    if (!CollectionUtils.isEmpty(styleMapping) && Strings.isNotEmpty(colData.getStyleName())) {
                        cell.setCellStyle(styleMapping.get(colData.getStyleName()));
                    }
                    handleMergeCell(sheet, colData);
                }
                colIdx++;
            }
            rowIdx++;
        }
    }

    // 取得儲存格樣式
    public static CellStyle getCellStyle(SXSSFWorkbook sxssfWorkbook, ExcelStyleInfo excelStyleInfo) {
        // 風格
        CellStyle cellStyle = sxssfWorkbook.createCellStyle();
        if (excelStyleInfo.getBgColor() != null) {
            cellStyle.setFillForegroundColor(excelStyleInfo.getBgColor()); // 設定背景色
        }
        if (excelStyleInfo.getBgPattern() != null) {
            cellStyle.setFillPattern(excelStyleInfo.getBgPattern()); // 填滿背景色
        }
        if (excelStyleInfo.getBorderTop() != null) {
            cellStyle.setBorderTop(excelStyleInfo.getBorderTop()); // 上框線
        }
        if (excelStyleInfo.getBorderBottom() != null) {
            cellStyle.setBorderBottom(excelStyleInfo.getBorderBottom()); // 下框線
        }
        if (excelStyleInfo.getBorderLeft() != null) {
            cellStyle.setBorderLeft(excelStyleInfo.getBorderLeft()); // 左框線
        }
        if (excelStyleInfo.getBorderRight() != null) {
            cellStyle.setBorderRight(excelStyleInfo.getBorderRight()); // 右框線
        }
        if (excelStyleInfo.getVerticalAlignment() != null) {
            cellStyle.setVerticalAlignment(excelStyleInfo.getVerticalAlignment()); // 垂直
        }
        if (excelStyleInfo.getHorizontalAlignment() != null) {
            cellStyle.setAlignment(excelStyleInfo.getHorizontalAlignment()); // 水平
        }
        // 字體
        Font font = sxssfWorkbook.createFont();
        if (excelStyleInfo.getFontName() != null) {
            font.setFontName(excelStyleInfo.getFontName()); // 字型
        }
        if (excelStyleInfo.getFontBold() != null) {
            font.setBold(excelStyleInfo.getFontBold()); // 是否為粗體字
        }
        if (excelStyleInfo.getFontSize() != null) {
            font.setFontHeightInPoints(excelStyleInfo.getFontSize()); // 字體大小
        }
        if (excelStyleInfo.getFontColor() != null) {
            font.setColor(excelStyleInfo.getFontColor()); // 字體顏色
        }
        cellStyle.setFont(font);
        // 千分位
        DataFormat dataFormat = sxssfWorkbook.createDataFormat();
        if (excelStyleInfo.getFormat() != null) {
            cellStyle.setDataFormat(dataFormat.getFormat(excelStyleInfo.getFormat()));
        }
        return cellStyle;
    }

    // 處理數值
    private static void handleCellVal(Cell cell, CellInfo colData) {
        Object cellVal = colData.getCellVal();
        if (cellVal instanceof String) {
            String strVal = cellVal.toString();
            strVal = strVal.length() >= 32767 ? strVal.substring(0, 32767) : strVal;
            cell.setCellValue(strVal);
        } else if (cellVal instanceof Number) {
            cell.setCellValue(((Number) cellVal).doubleValue());
        } else if (cellVal instanceof Date) {
            cell.setCellValue((Date) cellVal);
        } else if (cellVal instanceof Calendar) {
            cell.setCellValue((Calendar) cellVal);
        } else if (cellVal instanceof RichTextString) {
            cell.setCellValue((RichTextString) cellVal);
        } else {
            cell.setCellValue("");
        }
    }

    // 處理寬度
    private static void handleWidth(Sheet sheet, int colIdx, CellInfo colData) {
        if (colData.getWidth() > 0) {
            sheet.setColumnWidth(colIdx, colData.getWidth() * 256);
        }
    }

    // 處理高度
    private static void handleHeight(Row row, CellInfo colData) {
        if (colData.getHeight() > 0) {
            row.setHeightInPoints(colData.getHeight());
        }
    }

    // 處理超連結
    private static void handleUrl(Cell cell, CellInfo colData, CreationHelper creationHelper) {
        if (colData.isHasUrl()) {
            if (!colData.getUrlAddress().matches(URL_REGEX)) {
                return;
            }
            try {
                Hyperlink hyperlink = creationHelper.createHyperlink(HyperlinkType.URL);
                hyperlink.setAddress(colData.getUrlAddress());
                cell.setHyperlink(hyperlink);
            } catch (Exception e) {
                // log.error("Url含有非法字元");
            }
        }
    }

    // 處理合併儲存格
    private static void handleMergeCell(Sheet sheet, CellInfo colData) {
        if (colData.getCellRangeAddress() != null) {
            sheet.addMergedRegion(colData.getCellRangeAddress());
        }
    }

}
