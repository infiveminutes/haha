package com.haha.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageParam {
    private int pageSize;
    private int pageNum;
}
