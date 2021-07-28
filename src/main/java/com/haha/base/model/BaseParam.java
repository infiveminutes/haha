package com.haha.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseParam {
    private Integer pageSize;
    private Integer pageNum;
    private String token;
}
