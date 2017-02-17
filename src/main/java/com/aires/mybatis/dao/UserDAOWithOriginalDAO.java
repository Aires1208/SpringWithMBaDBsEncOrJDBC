package com.aires.mybatis.dao;

import com.aires.mybatis.po.User;

/**
 * Created by 10183966 on 2017/2/17.
 */

/*
* 原始DAO开发中存在的问题:
1) DAO实现方法体中存在很多过程性代码.
2) 调用SqlSession的方法(select/insert/update)需要指定Statement的id,存在硬编码,不利于代码维护.
* */
public interface UserDAOWithOriginalDAO {
    User selectUserById(Integer id) throws Exception;
}
