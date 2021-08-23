package com.haha.feed.controller;

import com.haha.base.model.BaseParam;
import com.haha.base.model.BaseResult;
import com.haha.feed.model.param.FeedListParam;
import com.haha.feed.service.UserFeedService;
import com.haha.user.login.LoginRequired;
import com.haha.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user_feed")
public class UserFeedController {

    @Autowired
    private UserFeedService userFeedService;

    // 获取用户的时间线
    @LoginRequired
    @PostMapping("/list")
    public BaseResult getUserFeed(HttpServletRequest request, BaseParam param) {
        User user = (User) request.getAttribute("user");
        param.checkPageParam();
        return BaseResult.createSuccess(userFeedService.getUserFeedList(user.getId(), param.getPageSize(), param.getPageNum()));
    }

    // 拉取消息 读扩散
    // todo 将这个过程放到消息队列中
    @LoginRequired
    @PostMapping("/read")
    public BaseResult getFollowigFeed(HttpServletRequest request, BaseParam param) {
        User user = (User) request.getAttribute("user");
        userFeedService.peekFollowing(user.getId());
        return BaseResult.SUCCESS;
    }
}
