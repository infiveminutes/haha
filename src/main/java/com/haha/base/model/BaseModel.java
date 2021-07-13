package com.haha.base.model;

import lombok.Data;

import java.util.Date;

@Data
public class BaseModel {
    private Date createTime;
    private Date updateTime;
    private Integer delete;  // 1表示逻辑删除 0表示未删除
}
