package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.TwTradeLogDao;
import com.example.entity.TwTradeLog;
import com.example.pojo.info.CellInfo;
import com.example.pojo.info.SheetInfo;
import com.example.service.ITwTradeLogService;
import com.example.utils.ExcelUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class TwTradeLogImpl extends ServiceImpl<TwTradeLogDao, TwTradeLog> implements ITwTradeLogService {

    @Override
    public void normalExcel(HttpServletResponse response) {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        TwTradeLog twTradeLog = list().get(0);
        List<List<CellInfo>> sheetData = new LinkedList<>();
        // 全部放入記憶體
        for (int i = 1; i <= 30000; i++) {
            addRowData(sheetData, twTradeLog);
        }
        // 再一起寫excel
        SheetInfo sheetInfo = SheetInfo.builder().SheetName("test:123").startRow(0).sheetData(sheetData).build();
        ExcelUtil.handleSheet(xssfWorkbook, sheetInfo);
        ExcelUtil.downloadExcel(xssfWorkbook, response);
    }

    @Override
    public void quickExcel(HttpServletResponse response) {
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();
        sxssfWorkbook.setCompressTempFiles(true);
        TwTradeLog twTradeLog = list().get(0);
        List<List<CellInfo>> sheetData = new LinkedList<>();
        for (int i = 1; i <= 30000; i++) {
            // 每100筆寫入excel
            addRowData(sheetData, twTradeLog);
            if (i % 5000 == 0) {
                SheetInfo sheetInfo = SheetInfo.builder().SheetName("test:456").startRow(i - 100).sheetData(sheetData).build();
                ExcelUtil.handleSheet(sxssfWorkbook, sheetInfo);
                sheetData.clear();
            }
        }
        ExcelUtil.downloadExcel(sxssfWorkbook, response);
    }

    @Override
    public List<TwTradeLog> getList(TwTradeLog twTradeLog) {
        if (twTradeLog == null) {
            return list();
        }
        LambdaQueryWrapper<TwTradeLog> lqw = new LambdaQueryWrapper<>();
        lqw.eq(twTradeLog.getId() != null, TwTradeLog::getId, twTradeLog.getId());
        lqw.eq(Strings.isNotEmpty(twTradeLog.getTradeDate()), TwTradeLog::getTradeDate, twTradeLog.getTradeDate());
        lqw.eq(Strings.isNotEmpty(twTradeLog.getStockId()), TwTradeLog::getStockId, twTradeLog.getStockId());
        lqw.eq(twTradeLog.getQuantity() != null, TwTradeLog::getQuantity, twTradeLog.getQuantity());
        lqw.eq(twTradeLog.getPayment() != null, TwTradeLog::getPayment, twTradeLog.getPayment());

        TwTradeLog one = getOne(lqw);
        System.out.println(new Gson().toJson(one));

        return list(lqw);
    }

    @Override
    public IPage<TwTradeLog> getPage(int curPage, int pageSize, TwTradeLog twTradeLog) {
        LambdaQueryWrapper<TwTradeLog> lqw = new LambdaQueryWrapper<>();
        lqw.like(Strings.isNotEmpty(twTradeLog.getTradeDate()), TwTradeLog::getTradeDate, twTradeLog.getTradeDate());
        lqw.like(Strings.isNotEmpty(twTradeLog.getStockId()), TwTradeLog::getStockId, twTradeLog.getStockId());
        lqw.like(twTradeLog.getQuantity() != null, TwTradeLog::getQuantity, twTradeLog.getQuantity());
        lqw.like(twTradeLog.getPayment() != null, TwTradeLog::getPayment, twTradeLog.getPayment());
        IPage<TwTradeLog> page = new Page<>(curPage, pageSize);
        return page(page, lqw);
    }

    public void addRowData(List<List<CellInfo>> sheetData, TwTradeLog twTradeLog) {
        List<CellInfo> rowData = new LinkedList<>();
        CellInfo colA = CellInfo.builder().cellVal(twTradeLog.getId()).build();
        CellInfo colB = CellInfo.builder().cellVal(twTradeLog.getTradeDate()).build();
        CellInfo colC = CellInfo.builder().cellVal(twTradeLog.getStockId()).build();
        CellInfo colD = CellInfo.builder().cellVal(twTradeLog.getQuantity()).build();
        CellInfo colE = CellInfo.builder().cellVal(twTradeLog.getPayment()).build();
        rowData.add(colA);
        rowData.add(colB);
        rowData.add(colC);
        rowData.add(colD);
        rowData.add(colE);
        sheetData.add(rowData);
    }

}
