package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.pojo.info.CellInfo;
import com.example.pojo.info.ExcelStyleInfo;
import com.example.pojo.info.SheetInfo;
import com.example.service.IUserService;
import com.example.utils.ExcelStyleUtil;
import com.example.utils.ExcelUtil;
import com.example.utils.ConstUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public void createNormalExcel() {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        Map<String, CellStyle> styleMapping = getStyleMapping(xssfWorkbook); // style對應
        List<List<CellInfo>> sheetData = new LinkedList<>();
        setFirstRowData(sheetData); // 第一列資料
        setRowData(sheetData); // 資料表資料
        generateFile(xssfWorkbook, styleMapping, sheetData);  // 輸出
    }

    private Map<String, CellStyle> getStyleMapping(XSSFWorkbook xssfWorkbook) {
        Map<String, CellStyle> styleMapping = new LinkedHashMap<>();
        // nameStyle 框線
        ExcelStyleInfo nameStyleInfo = ExcelStyleUtil.getExcelStyleInfo();
        CellStyle nameStyle = ExcelStyleUtil.getCellStyle(xssfWorkbook, nameStyleInfo);
        styleMapping.put("nameStyle", nameStyle);
        // urlStyle 文字色
        ExcelStyleInfo urlStyleInfo = ExcelStyleInfo.builder().fontColor(ExcelStyleUtil.COLOR_BLUE).build();
        CellStyle urlStyle = ExcelStyleUtil.getCellStyle(xssfWorkbook, urlStyleInfo);
        styleMapping.put("urlStyle", urlStyle);
        // ageColStyle 背景色
        ExcelStyleInfo ageColStyleInfo = ExcelStyleInfo.builder().bgColor(ExcelStyleUtil.COLOR_YELLOW).bgPattern(ExcelStyleUtil.FILL_SOLID).build();
        CellStyle ageColStyle = ExcelStyleUtil.getCellStyle(xssfWorkbook, ageColStyleInfo);
        styleMapping.put("ageColStyle", ageColStyle);
        return styleMapping;
    }

    private void setFirstRowData(List<List<CellInfo>> sheetData) {
        List<CellInfo> firstRowData = new LinkedList<>();
        String[] arr = {"id", "name", "age", "email"};
        for (String col : arr) {
            CellInfo cellInfo = CellInfo.builder().cellVal(col).build();
            if ("age".equals(col)) {
                cellInfo.setStyleName("ageColStyle");
            }
            if (col.equals("email")) {
                cellInfo.setWidth(30); // 寬度
            }
            firstRowData.add(cellInfo);
        }
        sheetData.add(firstRowData);
    }

    private void setRowData(List<List<CellInfo>> sheetData) {
        List<User> list = list();
        for (User user : list) {
            List<CellInfo> rowData = new LinkedList<>();
            CellInfo colA = CellInfo.builder().cellVal(user.getId()).build();
            CellInfo colB = CellInfo.builder().cellVal(user.getName()).styleName("nameStyle").build();
            CellInfo colC = CellInfo.builder().cellVal(user.getAge()).build();
            CellInfo colD = CellInfo.builder().cellVal(user.getEmail()).styleName("urlStyle").hasUrl(true).urlAddress("https://www.google.com/").build(); // 超連結
            rowData.add(colA);
            rowData.add(colB);
            rowData.add(colC);
            rowData.add(colD);
            sheetData.add(rowData);
        }
    }

    private void generateFile(XSSFWorkbook xssfWorkbook, Map<String, CellStyle> styleMapping, List<List<CellInfo>> sheetData) {
        SheetInfo sheetInfo = SheetInfo.builder().SheetName("user:123").startRow(0).sheetData(sheetData).styleMapping(styleMapping).build();
        ExcelUtil.handleSheet(xssfWorkbook, sheetInfo);
        String temp = LocalTime.now().toString().substring(0, 8).replace(":", "");
        String filePath = String.format(ConstUtil.DESKTOP, "user" + temp);
        ExcelUtil.createExcel(xssfWorkbook, filePath);
    }

}
