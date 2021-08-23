package com.haha.feed.model.param;

import com.haha.base.model.BaseParam;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class FeedListParam extends BaseParam {

    // 查询此用户所发的消息
    private String userId;
    private Long realUserId;

    public boolean checkParam() {
        super.checkPageParam();
        if(StringUtils.isBlank(userId)) {
            return false;
        }
        try{
            realUserId = Long.parseLong(userId);
        }catch (Exception e) {
            return false;
        }
        return true;
    }
}
