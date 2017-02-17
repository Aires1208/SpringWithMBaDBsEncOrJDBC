package com.aires.mybatis.dao;

import com.aires.mybatis.po.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by 10183966 on 2017/2/17.
 */
public interface UserDAOWithMapper {
    User selectUserById(Integer id) throws Exception;
    void updateUserById(@Param("id") Integer id,@Param("name") String name,@Param("password") String password) throws Exception;
    User selectUserByName(User user) throws Exception;
    User selectUserMapByName(String name) throws Exception;
    User selectUserByMap(Map<String, Object> map) throws Exception;

    List<User> selectUser(User user) throws Exception;
}
