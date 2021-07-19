package com.haha.feed.service.impl;

import com.haha.base.model.enums.SpreadTypeEnum;
import com.haha.feed.dao.FeedMapper;
import com.haha.feed.dao.UserFeedMapper;
import com.haha.feed.model.Feed;
import com.haha.feed.model.UserFeed;
import com.haha.feed.service.FeedService;
import com.haha.utils.IDGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    private FeedMapper feedMapper;

    @Autowired
    private UserFeedMapper userFeedMapper;

    @Autowired
    private IDGenService idGenService;

    @Override
    public int createFeed(Feed feed, SpreadTypeEnum typeEnum) {
        if(feed.getId() == null) {
            feed.setId(idGenService.getSnowFlakeId());
        }
        feedMapper.createFeed(feed);
        UserFeed selfFeed = new UserFeed();
        selfFeed.setId(idGenService.getSnowFlakeId());
        selfFeed.setUserId(feed.getUserId());
        selfFeed.setFeedId(feed.getId());
        // 这个时间不用太精确
        selfFeed.setFeedCreateTime(new Date());
        userFeedMapper.create(selfFeed);
        if(typeEnum == SpreadTypeEnum.WRITE) {
            writeSpread(feed);
        }
        return 0;
    }

    @Override
    public int createFeed(Feed feed) {
        return createFeed(feed, SpreadTypeEnum.READ);
    }

    @Override
    public List<Feed> getFeedListByUserId(Long userId, Long pageSize, Long pageNum, Date createTime) {
        Long skip = pageNum * pageSize;
        return feedMapper.getFeedByUserId(userId, skip, pageSize, createTime);
    }

    @Override
    public List<Feed> getFeedByIdList(List<Long> idList) {
        return feedMapper.getFeedListById(idList);
    }

    private void writeSpread(Feed feed){
        // todo write spread
    }
}
