package com.haha.feed.service;

import com.haha.feed.model.Feed;

import java.util.List;

public interface FeedService {
    int createFeed(Feed feed);
    List<Feed> getFeedListByUserId(Long userId);

}
