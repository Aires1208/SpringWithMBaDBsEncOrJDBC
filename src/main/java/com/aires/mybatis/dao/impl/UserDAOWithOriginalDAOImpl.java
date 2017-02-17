package com.aires.mybatis.dao.impl;

import com.aires.mybatis.dao.UserDAOWithOriginalDAO;
import com.aires.mybatis.po.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * Created by 10183966 on 2017/2/17.
 */
public class UserDAOWithOriginalDAOImpl implements UserDAOWithOriginalDAO {
    private SqlSessionFactory factory;

    public UserDAOWithOriginalDAOImpl(SqlSessionFactory factory) {

        this.factory = factory;
    }


    @Override
    public User selectUserById(Integer id) throws Exception {
        SqlSession session = factory.openSession();
        User user = session.selectOne("namespace.selectUserById", id);
        session.close();
        return user;
    }
}
