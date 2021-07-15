package com.haha.feed.dao;

import com.haha.feed.model.Feed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FeedMapper {
    int createFeed(Feed feed);
    int countFeedByUserId(Long userId);
    // todo 迁移到nosql 大数据量肯定不是存在关系型数据库中，且查询用户发的feed这么搜肯定有问题
    List<Feed> getFeedByUserId(@Param("userId") Long userId, @Param("skip") Long skip, @Param("limit") Long limit);
    List<Feed> getFeedListById(List<Long> list);
    int deleteFeed(Long feedId);
}
