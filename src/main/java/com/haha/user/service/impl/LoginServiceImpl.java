package com.haha.user.service.impl;

import com.haha.base.model.consts.ReturnCode;
import com.haha.user.dao.UserMapper;
import com.haha.user.model.User;
import com.haha.user.service.LoginService;
import com.haha.utils.GsonUtils;
import com.haha.utils.LogUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public int signUp(User user) {
        return ReturnCode.fail;
    }

    @Override
    public String signIn(User user) {
        LogUtils.COMMON.info("signIn {}", user);
        if(StringUtils.isBlank(user.getNickName()) || StringUtils.isBlank(user.getPasswordMd5())) {
            return null;
        }
        User loginUser = userMapper.getUserByName(user.getNickName());
        if(loginUser == null) {
            LogUtils.COMMON.error("signIn error empty userName {}", user);
            return null;
        }
        if(!user.getPasswordMd5().equals(loginUser.getPasswordMd5())) {
            LogUtils.COMMON.info("signIn error password not match {}", user);
            return null;
        }
        String token = getToken();
        try{
            redisTemplate.opsForValue().set(getUserKey(token), GsonUtils.gson.toJson(loginUser), 300, TimeUnit.SECONDS);
        }catch (Exception e) {
            LogUtils.COMMON.error(e);
            return null;
        }
        return token;
    }

    @Override
    public int signOut(String token) {
        try{
            redisTemplate.delete(getUserKey(token));
        }catch (Exception e) {
            LogUtils.COMMON.error(e);
            return ReturnCode.fail;
        }
        return ReturnCode.success;
    }

    @Override
    public User getUser(String token) {
        if(StringUtils.isBlank(token)) {
            return null;
        }
        User user;
        try{
            String userStr = redisTemplate.opsForValue().get(getUserKey(token));
            user = GsonUtils.gson.fromJson(userStr, User.class);
        }catch (Exception e) {
            LogUtils.COMMON.error(e);
            return null;
        }
        return user;
    }

    private String getToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private String getUserKey(String token) {
        return "login:" + token;
    }

    private String getUserKey() {
        return "login:" + getToken();
    }
}
