package com.haha.user.controller;

import com.haha.base.model.BaseResult;
import com.haha.user.model.User;
import com.haha.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public BaseResult login(User user) {
        String token = loginService.signIn(user);
        if(token == null) {
            return BaseResult.createError("登录失败");
        }
        return BaseResult.createSuccess(token);
    }
}
