package com.haha.user.service;

import com.haha.user.model.User;

public interface LoginService {
    int signUp(User user);

    String signIn(User user);

    int signOut(String token);

    User getUser(String code);
}
