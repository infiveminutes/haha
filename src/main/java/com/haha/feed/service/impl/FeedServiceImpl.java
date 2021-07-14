package com.haha.feed.service.impl;

import com.haha.feed.model.Feed;
import com.haha.feed.service.FeedService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedServiceImpl implements FeedService {
    @Override
    public int createFeed(Feed feed) {
        return 0;
    }

    @Override
    public List<Feed> getFeedListByUserId(Long userId) {
        return null;
    }
}
