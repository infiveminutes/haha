package com.example.demo;

import com.github.javafaker.Faker;
import com.haha.HahaApplication;
import com.haha.feed.model.Feed;
import com.haha.feed.model.UserFeed;
import com.haha.feed.service.FeedService;
import com.haha.feed.service.UserFeedService;
import com.haha.user.model.User;
import com.haha.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = HahaApplication.class)
@WebAppConfiguration
public class FakeTest {

    @Autowired
    UserService userService;

    @Autowired
    FeedService feedService;

    @Autowired
    UserFeedService userFeedService;

    private Executor executor = Executors.newFixedThreadPool(10);

    private Long userId = 79089173640458243L;
    private String userName = "七号";

    private User getUser() {
        return userService.findUserById(userId);
    }

    private Feed getFeed(User user) {
        Faker faker = new Faker(Locale.CHINA);
        Feed feed = new Feed();
        feed.setUserName(user.getNickName());
        feed.setUserId(user.getId());
        feed.setContent(faker.howIMetYourMother().catchPhrase());
        return feed;
    }

    private void pullFollowingFeed() {
        userFeedService.peekFollowing(userId);
    }

    private void createFeed() {
        List<User> followingList = userService.getFollowing(userId);
        for(User user: followingList) {
            Feed feed = getFeed(user);
            feedService.createFeed(feed);
        }
        userFeedService.peekFollowing(userId);
    }

    @Test
    public void startTest() {
        for(int i=0; i<100; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    createFeed();
                }
            });
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
        }
    }

}
