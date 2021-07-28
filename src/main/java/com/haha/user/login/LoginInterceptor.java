package com.haha.user.login;

import com.haha.user.model.User;
import com.haha.user.service.LoginService;
import com.haha.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(!HandlerMethod.class.isInstance(handler)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginRequired loginRequired = handlerMethod.getMethod().getDeclaredAnnotation(LoginRequired.class);
        if(loginRequired == null) {
            return true;
        }
        User user;
        try{
            user = loginService.getUser(request.getParameter("token"));
            if(null == user) {
                response.setStatus(400);
                return false;
            }
            request.setAttribute("user", user);
            return true;
        }catch (Exception e) {
            response.setStatus(500);
            LogUtils.ERROR.error(e);
        }
        return false;
    }
}
