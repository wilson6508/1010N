package com.hmdp.controller;


import com.hmdp.dto.LoginFormDTO;
import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.User;
import com.hmdp.entity.UserInfo;
import com.hmdp.service.IUserInfoService;
import com.hmdp.service.IUserService;
import com.hmdp.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    /**
     * 發送手機驗證碼
     */
    @PostMapping("/code")
    public Result sendCode(@RequestParam("phone") String phone, HttpSession session) {
        // 發送短信驗證碼並保存驗證碼
        return userService.sendCode(phone, session);
    }

    /**
     * 登錄功能
     * @param loginForm 登錄參數，包含手機號、驗證碼；或者手機號、密碼
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginFormDTO loginForm, HttpSession session) {
        // 實現登錄功能
        return userService.login(loginForm, session);
    }

    @GetMapping("/me")
    public Result me() {
        // 獲取當前登錄的用戶並返回
        UserDTO user = UserHolder.getUser();
        return Result.ok(user);
    }

}
