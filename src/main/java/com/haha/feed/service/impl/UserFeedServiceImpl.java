package com.haha.feed.service.impl;

import com.haha.feed.dao.FeedMapper;
import com.haha.feed.dao.UserFeedMapper;
import com.haha.feed.model.Feed;
import com.haha.feed.model.UserFeed;
import com.haha.feed.service.UserFeedService;
import com.haha.user.dao.UserMapper;
import com.haha.user.model.Following;
import com.haha.user.service.UserService;
import com.haha.utils.IDGenService;
import com.haha.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFeedServiceImpl implements UserFeedService {

    @Autowired
    private UserFeedMapper userFeedMapper;

    @Autowired
    private FeedMapper feedMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    IDGenService idGenService;

    @Transactional
    public int peekFollowing(Long userId, Following following) {
        // 分布式事务
        List<Feed> feedList = new ArrayList<>();
        long skip = 0;
        long limit = 50;
        long current = System.currentTimeMillis();
        /*
            如果getFeedByUserId方法执行的过程中，刚好有一条新的消息生成，因为事务原因没有查到
            但是写入的LastReadSpreadTime却是更后的时间，如下图
            上一次LastReadSpreadTime-----------(feed1)-------(feed2)------(feed3)|
            --------------------------------------------------------------------NowReadSpreadTime
            下一次查的时候就查不到这个feed3了
            所以这个NowReadSpreadTime的取值应该更早一点，可以减掉一段时间，消除事务的影响
        */
        current -= 5000;
        Date now = new Date(current);
        Date lastPeekTime = following.getLastReadSpreadTime();
        if(lastPeekTime == null) {
            lastPeekTime = new Date();
            lastPeekTime.setTime(1);
        }
        while(true) {

            List<Feed> temp = feedMapper.getFeedByUserId(following.getFollowing(), skip, limit, lastPeekTime);
            if(CollectionUtils.isEmpty(temp)) {
                break;
            }else {
                feedList.addAll(temp);
            }
            skip += limit;
        }
        following.setLastReadSpreadTime(now);
        for(Feed feed: feedList) {
            UserFeed userFeed = new UserFeed();
            userFeed.setFeedCreateTime(feed.getCreateTime());
            userFeed.setFeedId(feed.getId());
            userFeed.setUserId(userId);
            userFeed.setId(idGenService.getSnowFlakeId());
            userFeedMapper.create(userFeed);
            userMapper.updateFollowingReadTime(following);
        }
        return 0;
    }

    public int peekFollowing(Long userId) {
        List<Following> followingList = userMapper.getFollowing(userId);
        if(CollectionUtils.isEmpty(followingList)) {
            return 0;
        }
        for(Following following: followingList) {
            peekFollowing(userId, following);
        }
        return 0;
    }

    @Override
    public List<Feed> getUserFeedList(Long userId, Long pageSize, Long pageNum) {
        if(userId == null) {
            LogUtils.COMMON.error("UserFeedServiceImpl.getUserFeedList userId is null");
            return new ArrayList<>();
        }
        if(pageSize == null || pageSize > 50 || pageSize <= 0) {
            pageSize = 50L;
        }
        if(pageNum == null || pageNum <= 0) {
            pageNum = 1L;
        }
        pageNum -= 1;
        Long skip = pageSize * pageNum;
        List<UserFeed> userFeedList = userFeedMapper.getUserFeedList(userId, skip, pageSize);
        if(CollectionUtils.isEmpty(userFeedList)) {
            return new ArrayList<>();
        }
        List<Long> feedIdList = userFeedList.stream().map(UserFeed::getFeedId).collect(Collectors.toList());
        List<Feed> feedList = feedMapper.getFeedListById(feedIdList);
        if(CollectionUtils.isEmpty(feedList)) {
            return new ArrayList<>();
        }
        return feedList;
    }
}
