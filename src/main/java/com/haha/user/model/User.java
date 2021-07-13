package com.haha.user.model;

import com.haha.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=true)
public class User extends BaseModel {
    private Long id;
    private String nickName;
    private String passwordMd5;
    private String email;
    private String picture;
    private Integer sex;
    private Date birthday;
}
