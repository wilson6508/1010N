package com.pojo.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Author: RD3_wilson
 * CreateTime: 2023/9/27 - 上午 11:48
 * Version: 1.0
 * Description:
 */
@Entity
@Table(name = "ads_people_view_label", schema = "opv_auth_dev", catalog = "")
public class AdsPeopleViewLabelEntity {
    private int id;
    private String mainCategory;
    private String tagId;
    private String tag;
    private boolean isEnable;
    private boolean isDelete;
    private Integer numberOfAudience;
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
    @Column(name = "main_category")
    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }

    @Basic
    @Column(name = "tag_id")
    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @Basic
    @Column(name = "tag")
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Basic
    @Column(name = "is_enable")
    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    @Basic
    @Column(name = "is_delete")
    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    @Basic
    @Column(name = "number of audience")
    public Integer getNumberOfAudience() {
        return numberOfAudience;
    }

    public void setNumberOfAudience(Integer numberOfAudience) {
        this.numberOfAudience = numberOfAudience;
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
        AdsPeopleViewLabelEntity that = (AdsPeopleViewLabelEntity) o;
        return id == that.id && isEnable == that.isEnable && isDelete == that.isDelete && Objects.equals(mainCategory, that.mainCategory) && Objects.equals(tagId, that.tagId) && Objects.equals(tag, that.tag) && Objects.equals(numberOfAudience, that.numberOfAudience) && Objects.equals(createTime, that.createTime) && Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mainCategory, tagId, tag, isEnable, isDelete, numberOfAudience, createTime, updateTime);
    }
}
