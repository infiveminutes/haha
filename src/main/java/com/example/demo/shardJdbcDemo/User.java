package com.example.demo.shardJdbcDemo;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Long id;

    private String name;

    private String phone;

    private String email;

    private String password;

    private Integer sex;

    private Date createTime;
}

