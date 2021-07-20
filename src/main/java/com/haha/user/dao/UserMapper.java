package com.haha.user.dao;

import com.haha.user.model.Follower;
import com.haha.user.model.Following;
import com.haha.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    int create(User user);
    User getUserById(@Param("userId") Long userId);
    List<User> getUserListById(List<Long> userIdList);
    // 仅供测试使用
    List<User> getUserList();
    // 写入关注关系, 两个表分别为关注与被关注
    int follower(@Param("id") Long id, @Param("userId") Long userId, @Param("follower") Long follower);
    int following(@Param("id") Long id, @Param("userId") Long userId, @Param("following") Long following);

    int updateFollowingReadTime(Following following);
    // 查询粉丝
    List<Follower> getFollower(@Param("userId") Long userId);
    // 查询正在关注的人
    List<Following> getFollowing(@Param("userId") Long userId);

    User getUserByName(String userName);

}
