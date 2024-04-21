package com.hmdp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.LoginFormDTO;
import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.User;
import com.hmdp.mapper.UserMapper;
import com.hmdp.service.IUserService;
import com.hmdp.utils.RegexUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.hmdp.utils.RedisConstants.*;
import static com.hmdp.utils.SystemConstants.USER_NICK_NAME_PREFIX;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result sendCode(String phone, HttpSession session) {
        // 1. 驗證手機
        if (RegexUtils.isPhoneInvalid(phone)) {
            // 2. 如果不符 返回錯誤訊息
            return Result.fail("手機格式錯誤");
        }
        // 3. 符合 生成驗證碼
        String code = RandomUtil.randomNumbers(6);
        // 4. 保存驗證碼到redis
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + phone, code, LOGIN_CODE_TTL, TimeUnit.MINUTES);
        // 5. 發送驗證碼
        log.debug("發送驗證碼成功 驗證碼: {}", code);
        // 返回ok
        return Result.ok();
        // 4. 保存驗證碼到session
        // session.setAttribute("code", code);
    }

    @Override
    public Result login(LoginFormDTO loginForm, HttpSession session) {
        // 1. 驗證手機
        String phone = loginForm.getPhone();
        if (RegexUtils.isPhoneInvalid(phone)) {
            return Result.fail("手機格式錯誤");
        }
        // 2. 比對驗證碼
        // Object cacheCode = session.getAttribute("code");
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + phone);
        String code = loginForm.getCode();
        if (cacheCode == null || !cacheCode.equals(code)) {
            // 3. 不一致 報錯
            return Result.fail("驗證碼錯誤");
        }
        // 4. 一致 根據手機查用戶 select * from tb_user where phone = ?
        User user = query().eq("phone", phone).one();
        // 5. 判斷用戶存在與否
        if (user == null) {
            // 不存在 > 創建新用戶
            user = createUserWithPhone(phone);
        }
        // 6. 保存用戶到redis (uuid, user)
        String token = UUID.randomUUID().toString(true);
        String key = LOGIN_USER_KEY + token;
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        // Map<String, Object> userMap = BeanUtil.beanToMap(userDTO);
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
        stringRedisTemplate.opsForHash().putAll(key, userMap);
        stringRedisTemplate.expire(key, LOGIN_USER_TTL, TimeUnit.MINUTES);
        return Result.ok(token);
    }

    private User createUserWithPhone(String phone) {
        // 創建新用戶
        User user = new User();
        user.setPhone(phone);
        user.setNickName(USER_NICK_NAME_PREFIX + RandomUtil.randomString(10));
        // 存到資料庫
        save(user);
        return user;
    }
}
