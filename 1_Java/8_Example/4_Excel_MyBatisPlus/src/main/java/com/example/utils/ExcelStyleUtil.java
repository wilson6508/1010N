package com.example.utils;

import com.example.pojo.info.ExcelStyleInfo;
import org.apache.poi.ss.usermodel.*;

public class ExcelStyleUtil {

    private ExcelStyleUtil() {}

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


    public static CellStyle getCellStyle(Workbook workbook, ExcelStyleInfo excelStyleInfo) {
        // 風格
        CellStyle cellStyle = workbook.createCellStyle();
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
        Font font = workbook.createFont();
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
        DataFormat dataFormat = workbook.createDataFormat();
        if (excelStyleInfo.getFormat() != null) {
            cellStyle.setDataFormat(dataFormat.getFormat(excelStyleInfo.getFormat()));
        }
        return cellStyle;
    }

    public static ExcelStyleInfo getExcelStyleInfo() {
        return ExcelStyleInfo.builder()
                .borderTop(BORDER_THIN)
                .borderBottom(BORDER_THIN)
                .borderLeft(BORDER_THIN)
                .borderRight(BORDER_THIN)
                .verticalAlignment(VA_CENTER)
                .horizontalAlignment(HA_LEFT)
                .format(null)
                .build();
    }
}
