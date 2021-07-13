package com.haha.user.service.impl;

import com.github.javafaker.Faker;
import com.haha.user.dao.UserMapper;
import com.haha.user.model.Follower;
import com.haha.user.model.Following;
import com.haha.user.model.User;
import com.haha.user.service.UserService;
import com.haha.utils.IDGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    /**
     * todo
     * 1. 缓存
     * 2.
     */

    @Autowired
    IDGenService idGenService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public int createUser(User user) {
        return userMapper.create(user);
    }

    @Override
    public int createFake() {
        Faker faker = new Faker(Locale.CHINA);
        User fakeUser = new User();
        fakeUser.setId(idGenService.getSnowFlakeId());
        fakeUser.setNickName(faker.name().name());
        fakeUser.setPasswordMd5("5d41402abc4b2a76b9719d911017c592");
        fakeUser.setBirthday(new Date());
        fakeUser.setSex(faker.bool().bool() ? 0:1);
        return userMapper.create(fakeUser);
    }

    @Override
    public int createFakeRelation() {
        List<User> userList = userMapper.getUserList();
        Random random = new Random();
        for(User user: userList) {
            int index = random.nextInt(79);
            int num = random.nextInt(20);
            List<User> users = userList.subList(index, index+num);
            for(User tar: users) {
                follow(user.getId(), tar.getId());
            }
        }
        return 0;
    }

    @Override
    public User findUserById(Long userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public List<User> findUserListById(List<Long> userId) {
        if(CollectionUtils.isEmpty(userId)) {
            return new ArrayList<>();
        }
        return userMapper.getUserListById(userId);
    }

    @Override
    public List<User> getFollower(Long userId) {
        List<Follower> followerList = userMapper.getFollower(userId);
        List<Long> userIdList = followerList.stream().map(Follower::getFollower).collect(Collectors.toList());
        return findUserListById(userIdList);
    }

    @Override
    public List<User> getFollowing(Long userId) {
        List<Following> followerList = userMapper.getFollowing(userId);
        List<Long> userIdList = followerList.stream().map(Following::getFollowing).collect(Collectors.toList());
        return findUserListById(userIdList);
    }

    @Override
    @Transactional
    public int follow(Long userId, Long followingId) {
        // 需要写两个表的数据，follower存关注者 使用followingId分库， following存被关注的人 使用userId分库
        // 可能在不同的库需要分布式事务
        // 两个表都有唯一索引保证不重复
        userMapper.follower(idGenService.getSnowFlakeId(), followingId, userId);
        userMapper.following(idGenService.getSnowFlakeId(), userId, followingId);
        return 1;
    }
}
