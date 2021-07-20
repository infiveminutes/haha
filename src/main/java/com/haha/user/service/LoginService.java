package com.haha.user.service;

import com.haha.user.model.User;

public interface LoginService {
    int signUp(User user);

    int signIn(User user);

    int signOut(User user);
}
