package com.example.ztra;

import com.example.entity.AdsFacebookLabel;
import com.example.service.IAdsFacebookLabelService;
import com.example.utils.ConstUtil;
import com.example.ztra.dto.request.RawDataRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class RawDataService {

    public void downloadExcel(RawDataRequest rawDataRequest, HttpServletResponse response) {
        try {
            // 處理資料
            XSSFWorkbook xssfWorkbook = handleXSSFWorkbook(rawDataRequest);
            // 輸出
            OutputStream output = response.getOutputStream();
            xssfWorkbook.write(output);
            xssfWorkbook.close();
            output.flush();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createExcel(RawDataRequest rawDataRequest) {
        // 處理資料
        XSSFWorkbook xssfWorkbook = handleXSSFWorkbook(rawDataRequest);
        // 檔案位置
        String fileName = getRandomFileName();
        String filePath = String.format(ConstUtil.DESKTOP, fileName);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(filePath);
            xssfWorkbook.write(fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                xssfWorkbook.close();
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public LinkedList<HashMap<String, Object>> readJsonExcel(String fileName) {
        LinkedList<HashMap<String, Object>> linkedList = new LinkedList<>();
        try {
            String filePath = String.format(ConstUtil.DESKTOP, fileName);
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(filePath);
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            XSSFRow row0 = sheet.getRow(0);
            LinkedHashMap<Integer, String> idxAttrName = new LinkedHashMap<>();
            int lastCellNum = row0.getLastCellNum();
            for (int i = 0; i < lastCellNum; i++) {
                XSSFCell cell = row0.getCell(i);
                idxAttrName.put(i, cell.getStringCellValue());
            }
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);
                LinkedHashMap<String, Object> obj = new LinkedHashMap<>();
                for (int j = 0; j < lastCellNum; j++) {
                    XSSFCell cell = row.getCell(j);
                    obj.put(idxAttrName.get(j), cell.getStringCellValue());
                }
                linkedList.add(obj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linkedList;
    }

    @Resource
    private IAdsFacebookLabelService adsFacebookLabelService;

    public void insertToDb() throws IOException {
        String filePath = String.format(ConstUtil.DESKTOP, "表格架構123");
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(filePath);
        XSSFSheet sheet = xssfWorkbook.getSheet("表格架構");

        for (int i = 924; i <= 1545 ; i++) {
            System.out.println(i);
            XSSFRow row = sheet.getRow(i);
            String col2 = row.getCell(2).getStringCellValue();
            String mainCategory = row.getCell(3).getStringCellValue();
            String subCategory = row.getCell(4).getStringCellValue();
            String tag = row.getCell(5).getStringCellValue();
            int col6 = (int) row.getCell(6).getNumericCellValue();
            String numberOfAudience = String.valueOf(col6);

            AdsFacebookLabel entity = AdsFacebookLabel.builder()
                    .mainCategory(mainCategory)
                    .subCategory(subCategory)
                    .tag(tag)
                    .isEnable(true)
                    .isDelete(false)
                    .numberOfAudience(numberOfAudience)
                    .createTime(new Timestamp(System.currentTimeMillis()))
                    .build();
            adsFacebookLabelService.save(entity);
        }
    }

    public XSSFWorkbook handleXSSFWorkbook(RawDataRequest rawDataRequest) {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        Sheet sheet = xssfWorkbook.createSheet("sheet1");
        Map<Integer, String> colMap = rawDataRequest.getColMap();
        int size = colMap.keySet().size();
        // 放欄位
        Row row0 = sheet.createRow(0);
        for (int i = 0; i < size; i++) {
            String attrName = colMap.get(i);
            Cell cell = row0.createCell(i);
            cell.setCellValue(attrName);
        }
        // 放資料
        int rowIdx = 1;
        List<Map<String, Object>> dataList = rawDataRequest.getDataList();
        for (Map<String, Object> obj : dataList) {
            Row row = sheet.createRow(rowIdx);
            for (int i = 0; i < size; i++) {
                String attrName = colMap.get(i);
                Object o = obj.get(attrName);
                Cell cell = row.createCell(i);
                cell.setCellValue(o.toString());
            }
            rowIdx++;
        }
        return xssfWorkbook;
    }

    public String getRandomFileName() {
        StringBuilder stringBuilder = new StringBuilder();
        LocalDate now = LocalDate.now();
        String substring = now.toString().replace("-", "").substring(4);
        stringBuilder.append(substring);
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            int rand = random.nextInt(26);
            stringBuilder.append((char) (rand + 'a'));
        }
        return stringBuilder.toString();
    }

}
