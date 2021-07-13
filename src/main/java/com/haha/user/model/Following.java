package com.haha.user.model;

import com.haha.base.model.BaseModel;
import lombok.Data;

@Data
public class Following extends BaseModel {
    private Long id;
    private Long userId;
    private Long following;
}
