package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.User;

public interface IUserService extends IService<User> {
    void createNormalExcel();
}
