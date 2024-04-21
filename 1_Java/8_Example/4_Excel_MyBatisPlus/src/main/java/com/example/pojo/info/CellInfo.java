package com.example.pojo.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.util.CellRangeAddress;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CellInfo {
    private Object cellVal;
    private boolean hasUrl;
    private String urlAddress;
    private int width;
    private int height;
    private CellRangeAddress cellRangeAddress;
    private String styleName;
}
