package com.haha.feed.service;

import com.haha.base.model.enums.SpreadTypeEnum;
import com.haha.feed.model.Feed;

import java.util.List;

public interface FeedService {
    int createFeed(Feed feed, SpreadTypeEnum typeEnum);
    List<Feed> getFeedListByUserId(Long userId, Long pageSize, Long pageNum);

}
