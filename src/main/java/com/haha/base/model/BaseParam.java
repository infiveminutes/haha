package com.haha.base.model;

import lombok.Data;

@Data
public class BaseParam {
    private Long pageSize;
    private Long pageNum;
    private String token;

    public void checkPageParam() {
        if(pageSize == null || pageSize <= 0 || pageSize > 100) {
            pageSize = 20L;
        }
        if(pageNum == null || pageNum <= 0) {
            this.pageNum = 1L;
        }
    }
}
