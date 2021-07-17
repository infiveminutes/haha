package com.haha.feed.dao;

import com.haha.feed.model.UserFeed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserFeedMapper {
    int create(UserFeed userFeed);
    List<UserFeed> getUserFeedList(@Param("userId") Long userId, @Param("skip") Long skip, @Param("limit") Long limit);
}
