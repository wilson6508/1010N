package com.example.utils;

import com.example.pojo.info.CellInfo;
import com.example.pojo.info.SheetInfo;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    private ExcelUtil() {
    }

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

    public static void downloadExcel(Workbook workbook, HttpServletResponse response) {
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
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
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
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
                    handleUrl(cell, colData, creationHelper);
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

    public void createExcel(SheetInfo sheetInfo) {
        XSSFWorkbook xssfWorkbook = null;
        FileOutputStream fileOutputStream = null;
        try {
            xssfWorkbook = new XSSFWorkbook();
            handleSheet(xssfWorkbook, sheetInfo);
            String filePath = String.format(ConstUtil.DESKTOP, "normal");
            fileOutputStream = new FileOutputStream(filePath);
            xssfWorkbook.write(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (xssfWorkbook != null) {
                    xssfWorkbook.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void downloadExcel(SheetInfo sheetInfo, HttpServletResponse response) {
        XSSFWorkbook xssfWorkbook = null;
        OutputStream outputStream = null;
        try {
            // 處理資料
            xssfWorkbook = new XSSFWorkbook();
            handleSheet(xssfWorkbook, sheetInfo);
            // 輸出
            outputStream = response.getOutputStream();
            xssfWorkbook.write(outputStream);
        } catch (IOException e) {
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

    public void createQuickExcel(SheetInfo sheetInfo) {
        SXSSFWorkbook sxssfWorkbook = null;
        FileOutputStream fileOutputStream = null;
        try {
            sxssfWorkbook = new SXSSFWorkbook();
            sxssfWorkbook.setCompressTempFiles(true);
            handleSheet(sxssfWorkbook, sheetInfo);
            String filePath = String.format(ConstUtil.DESKTOP, "quick");
            fileOutputStream = new FileOutputStream(filePath);
            sxssfWorkbook.write(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (sxssfWorkbook != null) {
                    sxssfWorkbook.dispose(); // 刪除臨時文件 否則磁盤可能會被寫滿
                    sxssfWorkbook.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void downloadQuickExcel(SheetInfo sheetInfo, HttpServletResponse response) {
        SXSSFWorkbook sxssfWorkbook = null;
        OutputStream outputStream = null;
        try {
            // 處理資料
            sxssfWorkbook = new SXSSFWorkbook();
            sxssfWorkbook.setCompressTempFiles(true);
            handleSheet(sxssfWorkbook, sheetInfo);
            // 輸出
            outputStream = response.getOutputStream();
            sxssfWorkbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (sxssfWorkbook != null) {
                    sxssfWorkbook.dispose(); // 刪除臨時文件 否則磁盤可能會被寫滿
                    sxssfWorkbook.close();
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
