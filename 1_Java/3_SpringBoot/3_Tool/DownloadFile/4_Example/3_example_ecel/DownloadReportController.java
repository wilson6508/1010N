package com.eland.controller.report;

import com.eland.pojo.dto.insight.InsightApiRequestDTO;
import com.eland.pojo.info.UserInfo;
import com.eland.pojo.info.report.GeneralReportTaskInfo;
import com.eland.service.insight.report.DownloadReportService;
import com.eland.service.insight.report.GeneralReportTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/reportFile")
public class DownloadReportController {

    @Autowired
    DownloadReportService downloadReportService;
    @Autowired
    GeneralReportTaskService generalReportTaskService;

    @RequestMapping(value = "/downloadHtml", method = {RequestMethod.POST, RequestMethod.GET})
    public void downloadHtml(HttpServletRequest request, HttpServletResponse response) {
        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
        InsightApiRequestDTO insightApiRequestDTO = (InsightApiRequestDTO) request.getAttribute("requestDTO");
        InsightApiRequestDTO.ParameterBean parameterBean = insightApiRequestDTO.getParameter();
        GeneralReportTaskInfo taskInfo = parameterBean.getGeneralReportTaskInfo();
        downloadReportService.downloadReportHtml(userInfo, taskInfo, response);
    }

    @RequestMapping(value = "/downloadExcel", method = {RequestMethod.POST, RequestMethod.GET})
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response) {
        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
        InsightApiRequestDTO insightApiRequestDTO = (InsightApiRequestDTO) request.getAttribute("requestDTO");
        InsightApiRequestDTO.ParameterBean parameterBean = insightApiRequestDTO.getParameter();
        GeneralReportTaskInfo taskInfo = parameterBean.getGeneralReportTaskInfo();
        downloadReportService.downloadReportExcel(userInfo, taskInfo, response);
    }

}