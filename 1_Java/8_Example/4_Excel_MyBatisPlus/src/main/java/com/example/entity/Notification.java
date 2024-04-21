package com.example.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("tb_notification")
public class Notification {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private int userId;
    private String title;
    private String content;
//    @TableField(insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private String createTime;
//    @TableField(insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private String updateTime;
    private int status;
}
