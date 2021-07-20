package com.haha.user.service.impl;

import com.haha.base.model.consts.ReturnCode;
import com.haha.user.dao.UserMapper;
import com.haha.user.model.User;
import com.haha.user.service.LoginService;
import com.haha.utils.LogUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedissonClient redisCli;

    @Override
    public int signUp(User user) {
        return ReturnCode.fail;
    }

    @Override
    public int signIn(User user) {
        LogUtils.COMMON.info("signIn {}", user);
        if(StringUtils.isBlank(user.getNickName()) || StringUtils.isBlank(user.getPasswordMd5())) {
            return ReturnCode.fail;
        }
        User loginUser = userMapper.getUserByName(user.getNickName());
        if(loginUser == null) {
            LogUtils.COMMON.error("signIn error empty userName {}", user);
            return ReturnCode.fail;
        }
        try{
            RBucket<String> aaa = redisCli.getBucket(getUserKey(user.getId()));
            aaa.set(user.toString(), 300, TimeUnit.SECONDS);
        }catch (Exception e) {
            LogUtils.COMMON.error(e);
            return ReturnCode.fail;
        }
        return ReturnCode.success;
    }

    @Override
    public int signOut(User user) {
        return 0;
    }

    private String getUserKey(Long userId) {
        return "login:" + userId;
    }
}
