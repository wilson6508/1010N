package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.TwTradeLog;
import com.example.service.ITwTradeLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/twTradeLog")
public class TwTradeLogController {

    @Resource
    private ITwTradeLogService twTradeLogService;

    @GetMapping("/normalExcel")
    public void normalExcel(HttpServletResponse response) {
        twTradeLogService.normalExcel(response);
    }

    @GetMapping("/quickExcel")
    public void quickExcel(HttpServletResponse response) {
        twTradeLogService.quickExcel(response);
    }

    @GetMapping
    public List<TwTradeLog> getList(TwTradeLog twTradeLog) {
        return twTradeLogService.getList(twTradeLog);
    }

    @GetMapping("/{id}")
    public TwTradeLog getById(@PathVariable Integer id) {
        return twTradeLogService.getById(id);
    }

    @GetMapping("/{currPage}/{pageSize}")
    public IPage<TwTradeLog> getPage(@PathVariable int currPage, @PathVariable int pageSize, TwTradeLog twTradeLog) {
        IPage<TwTradeLog> page = twTradeLogService.getPage(currPage, pageSize, twTradeLog);
        if (currPage > page.getPages()) {
            page = twTradeLogService.getPage((int) page.getPages(), pageSize, twTradeLog);
        }
        return page;
    }

}
