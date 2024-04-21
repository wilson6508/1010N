package com.example.pojo.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelStyleInfo {
    private String fontName;
    private Boolean fontBold;
    private Short fontSize;
    private Short fontColor;
    private Short bgColor;
    private FillPatternType bgPattern;
    private BorderStyle borderTop;
    private BorderStyle borderBottom;
    private BorderStyle borderLeft;
    private BorderStyle borderRight;
    private VerticalAlignment verticalAlignment;
    private HorizontalAlignment horizontalAlignment;
    private String format;
}
