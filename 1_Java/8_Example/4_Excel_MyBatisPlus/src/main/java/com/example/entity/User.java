package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_user")
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
