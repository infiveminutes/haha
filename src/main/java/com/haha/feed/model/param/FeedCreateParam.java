package com.haha.feed.model.param;

import com.haha.base.model.BaseParam;
import lombok.Data;

@Data
public class FeedCreateParam extends BaseParam {
    private String content;
    private String pictureList;
    private String mediaLink;
}
