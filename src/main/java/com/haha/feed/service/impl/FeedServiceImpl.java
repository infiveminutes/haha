package com.haha.feed.service.impl;

import com.haha.base.model.enums.SpreadTypeEnum;
import com.haha.feed.dao.FeedMapper;
import com.haha.feed.model.Feed;
import com.haha.feed.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    private FeedMapper feedMapper;

    @Override
    public int createFeed(Feed feed, SpreadTypeEnum typeEnum) {
        feedMapper.createFeed(feed);
        if(typeEnum == SpreadTypeEnum.WRITE) {
            writeSpread(feed);
        }
        return 0;
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
