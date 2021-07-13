package com.haha.user.service;

import com.haha.user.model.User;

import java.util.List;

public interface UserService {
    int createUser(User user);

    User findUserById(Long userId);

    List<User> findUserListById(List<Long> userId);

    // 查询粉丝
    List<User> getFollower(Long userId);

    // 查询关注的人
    List<User> getFollowing(Long userId);

    int follow(Long userId, Long followingId);

    /*
    create fake data
     */
    int createFake();

    int createFakeRelation();

}
