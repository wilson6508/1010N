package com.example.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class AdsFacebookLabel {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String mainCategory;
    private String subCategory;
    private String tag;
    private boolean isEnable;
    private boolean isDelete;
    private String numberOfAudience;
    private Timestamp createTime;
    @TableField(insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private String updateTime;
}
