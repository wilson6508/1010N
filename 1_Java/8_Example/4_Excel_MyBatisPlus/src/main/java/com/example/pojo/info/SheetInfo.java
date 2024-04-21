package com.example.pojo.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.CellStyle;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SheetInfo {
    private String SheetName;
    private int startRow;
    private Map<String, CellStyle> styleMapping;
    private List<List<CellInfo>> sheetData;
}
