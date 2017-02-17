package com.aires.databasesource.client;

import com.aires.mybatis.dao.UserDAOWithOriginalDAO;
import com.aires.mybatis.dao.impl.UserDAOWithOriginalDAOImpl;
import com.aires.mybatis.po.User;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

/**
 * Created by 10183966 on 2017/2/17.
 */
public class MyBatisClient {
    @Test
    public void originalClient() throws Exception {
        UserDAOWithOriginalDAO dao = new UserDAOWithOriginalDAOImpl(new SqlSessionFactoryBuilder().
                build(ClassLoader.getSystemResourceAsStream("mybatis/mybatis-configuration.xml")));
        User user = dao.selectUserById(1);
        System.out.println(user);
    }
}
