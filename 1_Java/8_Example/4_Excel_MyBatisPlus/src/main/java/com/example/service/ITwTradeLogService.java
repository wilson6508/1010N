package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.TwTradeLog;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ITwTradeLogService extends IService<TwTradeLog> {
    void normalExcel(HttpServletResponse response);

    void quickExcel(HttpServletResponse response);

    List<TwTradeLog> getList(TwTradeLog twTradeLog);

    IPage<TwTradeLog> getPage(int curPage, int pageSize, TwTradeLog twTradeLog);
}
