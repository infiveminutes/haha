package com.haha.user.controller;

import com.haha.base.model.BaseParam;
import com.haha.base.model.BaseResult;
import com.haha.user.login.LoginRequired;
import com.haha.user.model.User;
import com.haha.user.service.LoginService;
import com.haha.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @LoginRequired
    @PostMapping("/followers")
    public BaseResult getFollowerList(HttpServletRequest request, BaseParam param) {
        User user = (User) request.getAttribute("user");
        return BaseResult.createSuccess(userService.getFollower(user.getId()));
    }


}
