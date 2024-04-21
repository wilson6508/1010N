package com.pojo.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Author: RD3_wilson
 * CreateTime: 2023/11/21 - 下午 03:59
 * Version: 1.0
 * Description:
 */
@Entity
@Table(name = "schedule_download_task", schema = "opv_auth_dev", catalog = "")
public class ScheduleDownloadTaskEntity {
    private int id;
    private int configId;
    private int topicId;
    private String fileMonth;
    private int fileNum;
    private Timestamp updateTime;
    private int status;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "config_id")
    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    @Basic
    @Column(name = "topic_id")
    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    @Basic
    @Column(name = "file_month")
    public String getFileMonth() {
        return fileMonth;
    }

    public void setFileMonth(String fileMonth) {
        this.fileMonth = fileMonth;
    }

    @Basic
    @Column(name = "file_num")
    public int getFileNum() {
        return fileNum;
    }

    public void setFileNum(int fileNum) {
        this.fileNum = fileNum;
    }

    @Basic
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleDownloadTaskEntity that = (ScheduleDownloadTaskEntity) o;
        return id == that.id && configId == that.configId && topicId == that.topicId && fileNum == that.fileNum && status == that.status && Objects.equals(fileMonth, that.fileMonth) && Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, configId, topicId, fileMonth, fileNum, updateTime, status);
    }
}
