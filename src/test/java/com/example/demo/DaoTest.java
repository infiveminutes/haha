package com.example.demo;

import com.example.demo.shardJdbcDemo.User;
import com.haha.HahaApplication;
import com.haha.feed.dao.FeedMapper;
import com.haha.feed.model.Feed;
import com.haha.user.dao.UserMapper;
import com.haha.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.IdGenerator;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = HahaApplication.class)
@WebAppConfiguration
public class DaoTest {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    FeedMapper feedMapper;

//    @Resource
//    IdGenerator idGenerator;

    @Test
    public void fakeUser() {
        userService.createFakeRelation();
    }

    @Test
    public void getUser() {
        System.out.println(userMapper.getUserListById(Arrays.asList(79089170134020097L, 79089179248242698L)));
    }

    @Test
    public void followTest() {
        userService.follow(79089180946935820L, 79089179248242698L);
    }

    @Test
    public void getFollower() {
        System.out.println(userService.getFollower(79089219194794063L));
    }

    @Test
    public void getFeed() {
        List<Feed> feedList = feedMapper.getFeedByUserId(79089179248242698L, 0L, 100L, new Date());
        System.out.println(feedList);
    }

    @Test
    public void feedTest() {
        Feed feed = new Feed();
        feed.setId(111L);
        feed.setUserId(79089179248242698L);
        feed.setUserName("梁鑫鹏");
        feed.setContent("hello content");
        feedMapper.createFeed(feed);
    }
}
