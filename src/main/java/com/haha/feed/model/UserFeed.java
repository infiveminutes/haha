package com.haha.feed.model;

import com.haha.base.model.BaseModel;
import lombok.Data;

import java.util.Date;

@Data
public class UserFeed extends BaseModel {
    private Long id;
    private Long feedId;
    private Date feedCreateTime;
}
