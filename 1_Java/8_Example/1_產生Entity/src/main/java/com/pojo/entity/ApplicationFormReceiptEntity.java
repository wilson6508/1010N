package com.pojo.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Author: RD3_wilson
 * CreateTime: 2023/7/27 - 上午 09:52
 * Version: 1.0
 * Description:
 */
@Entity
@Table(name = "application_form_receipt", schema = "opv_auth_dev", catalog = "")
public class ApplicationFormReceiptEntity {
    private int id;
    private int typeId;
    private String code;
    private int fillerId;
    private String fillerName;
    private String fillerDept;
    private Timestamp createTime;
    private String cusNumber;
    private String cusName;
    private String product;
    private String orderId;
    private String status;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "type_id")
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "filler_id")
    public int getFillerId() {
        return fillerId;
    }

    public void setFillerId(int fillerId) {
        this.fillerId = fillerId;
    }

    @Basic
    @Column(name = "filler_name")
    public String getFillerName() {
        return fillerName;
    }

    public void setFillerName(String fillerName) {
        this.fillerName = fillerName;
    }

    @Basic
    @Column(name = "filler_dept")
    public String getFillerDept() {
        return fillerDept;
    }

    public void setFillerDept(String fillerDept) {
        this.fillerDept = fillerDept;
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
    @Column(name = "cus_number")
    public String getCusNumber() {
        return cusNumber;
    }

    public void setCusNumber(String cusNumber) {
        this.cusNumber = cusNumber;
    }

    @Basic
    @Column(name = "cus_name")
    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    @Basic
    @Column(name = "product")
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Basic
    @Column(name = "order_id")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationFormReceiptEntity that = (ApplicationFormReceiptEntity) o;
        return id == that.id && typeId == that.typeId && fillerId == that.fillerId && Objects.equals(code, that.code) && Objects.equals(fillerName, that.fillerName) && Objects.equals(fillerDept, that.fillerDept) && Objects.equals(createTime, that.createTime) && Objects.equals(cusNumber, that.cusNumber) && Objects.equals(cusName, that.cusName) && Objects.equals(product, that.product) && Objects.equals(orderId, that.orderId) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeId, code, fillerId, fillerName, fillerDept, createTime, cusNumber, cusName, product, orderId, status);
    }
}
