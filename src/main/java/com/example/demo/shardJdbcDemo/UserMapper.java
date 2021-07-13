package com.example.demo.shardJdbcDemo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 保存
     */
    void save(User user);

    /**
     * 查询
     * @param ids
     * @return
     */
    List<User> getByIds(@Param("ids") List<Long> ids);
}

