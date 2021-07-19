package com.example.demo;

import com.github.javafaker.Faker;
import com.haha.HahaApplication;
import com.haha.feed.model.Feed;
import com.haha.feed.service.FeedService;
import com.haha.feed.service.UserFeedService;
import com.haha.user.model.User;
import com.haha.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.List;
import java.util.Locale;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = HahaApplication.class)
@WebAppConfiguration
public class ServiceTest {

    @Autowired
    UserFeedService userFeedService;

    @Autowired
    FeedService feedService;

    @Autowired
    UserService userService;

    private Long userId = 79089185799745556L;

    @Test
    public void createFeed() {
        List<User> userList = userService.getFollower(userId);
        System.out.println(String.format("被%d人关注", userList.size()));
        feedService.createFeed(getFeed(userId));
        for(User user: userList) {
            userFeedService.peekFollowing(user.getId());
        }
        System.out.println(userList);
    }

    @Test
    public void getUserFeedByFollowingId() {
        List<User> userList = userService.getFollower(userId);
        for(User user: userList) {
            getUserFeed(user.getId());
        }
    }

    @Test
    public void getFeedById() {
        System.out.println(feedService.getFeedListByUserId(userId, 100L, 0L, new Date(0)));
    }

    @Test
    public void getUserFeed() {
        System.out.println(userFeedService.getUserFeedList(79089196470054952L, 100L, 0L));
    }

    private void getUserFeed(Long userId) {
        System.out.println(userFeedService.getUserFeedList(userId, 100L, 0L));
    }

    private Feed getFeed(Long userId) {
        Faker faker = new Faker(Locale.CHINA);
        Feed feed = new Feed();
        User user = userService.findUserById(userId);
        feed.setUserName(user.getNickName());
        feed.setUserId(userId);
        feed.setContent(faker.howIMetYourMother().catchPhrase());
        return feed;
    }

}
