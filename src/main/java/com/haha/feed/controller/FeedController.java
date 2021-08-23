package com.haha.feed.controller;

import com.haha.base.model.BaseParam;
import com.haha.base.model.BaseResult;
import com.haha.feed.model.Feed;
import com.haha.feed.model.param.FeedCreateParam;
import com.haha.feed.model.param.FeedListParam;
import com.haha.feed.service.FeedService;
import com.haha.user.login.LoginRequired;
import com.haha.user.model.User;
import com.haha.utils.IDGenService;
import com.haha.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    private FeedService feedService;

    @Autowired
    private IDGenService idGenService;

    // 创建消息
    @LoginRequired
    @PostMapping("create")
    public BaseResult createFeed(HttpServletRequest request, FeedCreateParam param) {
        User user = (User) request.getAttribute("user");
        Feed feed = new Feed();
        feed.setId(idGenService.getSnowFlakeId());
        feed.setUserId(user.getId());
        feed.setUserName(user.getNickName());
        feed.setContent(param.getContent());
        feed.setMediaLink(param.getMediaLink());
        feed.setPictureList(param.getPictureList());
        try{
            feedService.createFeed(feed);
        }catch (Exception e) {
            LogUtils.ERROR.error(String.format("getFeedList error, %s", param), e);
            return BaseResult.SYSTEM_ERROR;
        }
        return BaseResult.SUCCESS;
    }

    // todo 删除消息
    @PostMapping("delete")
    public BaseResult deleteFeed(HttpServletRequest request, BaseParam param) {
        return BaseResult.SUCCESS;
    }


    // 获取用户(userId)的消息列表
    @PostMapping("/list")
    public BaseResult getFeedList(HttpServletRequest request, FeedListParam param) {
        param.checkParam();
        return BaseResult.createSuccess(feedService.getFeedListByUserId(param.getRealUserId(), param.getPageSize(), param.getPageNum(), null));
    }
}
