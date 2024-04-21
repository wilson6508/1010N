package com.pojo.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Author: RD3_wilson
 * CreateTime: 2023/9/20 - 下午 01:45
 * Version: 1.0
 * Description:
 */
@Entity
@Table(name = "ai_people_package_task", schema = "opv_auth_dev", catalog = "")
public class AiPeoplePackageTaskEntity {
    private int id;
    private String tagFilters;
    private byte status;
    private String fileUrl;
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
    @Column(name = "tag_filters")
    public String getTagFilters() {
        return tagFilters;
    }

    public void setTagFilters(String tagFilters) {
        this.tagFilters = tagFilters;
    }

    @Basic
    @Column(name = "status")
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "file_url")
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
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
        AiPeoplePackageTaskEntity that = (AiPeoplePackageTaskEntity) o;
        return id == that.id && status == that.status && Objects.equals(tagFilters, that.tagFilters) && Objects.equals(fileUrl, that.fileUrl) && Objects.equals(createTime, that.createTime) && Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tagFilters, status, fileUrl, createTime, updateTime);
    }
}
