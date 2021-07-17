package com.haha.feed.service;

import com.haha.feed.model.Feed;
import com.haha.user.model.Following;

import java.util.List;

public interface UserFeedService {
    /*
    todo
    1. 新消息提醒
    2. 已读的消息
     */

    int peekFollowing(Long userId, Following following);

    int peekFollowing(Long userId);

    List<Feed> getUserFeedList(Long userId, Long pageSize, Long pageNum);
}
