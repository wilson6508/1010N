package com.pojo.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Author: RD3_wilson
 * CreateTime: 2023/7/28 - 上午 09:50
 * Version: 1.0
 * Description:
 */
@Entity
@Table(name = "oms_search_api_log", schema = "opv_auth_dev", catalog = "")
public class OmsSearchApiLogEntity {
    private int id;
    private int accountId;
    private String ipAddress;
    private String moduleName;
    private String searchUrl;
    private String searchJson;
    private String orderNumber;
    private Integer errorCode;
    private String errorMessage;
    private Double queryTime;
    private Timestamp createTime;
    private Timestamp updateTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "account_id")
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Basic
    @Column(name = "ip_address")
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Basic
    @Column(name = "module_name")
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @Basic
    @Column(name = "search_url")
    public String getSearchUrl() {
        return searchUrl;
    }

    public void setSearchUrl(String searchUrl) {
        this.searchUrl = searchUrl;
    }

    @Basic
    @Column(name = "search_json")
    public String getSearchJson() {
        return searchJson;
    }

    public void setSearchJson(String searchJson) {
        this.searchJson = searchJson;
    }

    @Basic
    @Column(name = "order_number")
    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Basic
    @Column(name = "error_code")
    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @Basic
    @Column(name = "error_message")
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Basic
    @Column(name = "query_time")
    public Double getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(Double queryTime) {
        this.queryTime = queryTime;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OmsSearchApiLogEntity that = (OmsSearchApiLogEntity) o;
        return id == that.id && accountId == that.accountId && Objects.equals(ipAddress, that.ipAddress) && Objects.equals(moduleName, that.moduleName) && Objects.equals(searchUrl, that.searchUrl) && Objects.equals(searchJson, that.searchJson) && Objects.equals(orderNumber, that.orderNumber) && Objects.equals(errorCode, that.errorCode) && Objects.equals(errorMessage, that.errorMessage) && Objects.equals(queryTime, that.queryTime) && Objects.equals(createTime, that.createTime) && Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, ipAddress, moduleName, searchUrl, searchJson, orderNumber, errorCode, errorMessage, queryTime, createTime, updateTime);
    }
}
