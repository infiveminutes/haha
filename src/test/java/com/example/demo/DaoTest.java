package com.example.demo;

import com.example.demo.shardJdbcDemo.User;
import com.haha.HahaApplication;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = HahaApplication.class)
@WebAppConfiguration
public class DaoTest {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

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

}
