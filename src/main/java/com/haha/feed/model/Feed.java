package com.haha.feed.model;

import com.haha.base.model.BaseModel;
import lombok.Data;

@Data
public class Feed extends BaseModel {
    private Long id;
    private Long userId;
    private String userName;
    private String content;
    private String pictureList;
    private String mediaLink;
}
