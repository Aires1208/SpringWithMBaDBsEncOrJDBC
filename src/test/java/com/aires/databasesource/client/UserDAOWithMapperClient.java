package com.aires.databasesource.client;

import com.aires.mybatis.dao.UserDAOWithMapper;
import com.aires.mybatis.po.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 10183966 on 2017/2/17.
 */
public class UserDAOWithMapperClient {
    private SqlSession session;

    private SqlSessionFactory factory;

    @Before
    public void setUp() {
        factory = new SqlSessionFactoryBuilder().
                build(ClassLoader.getSystemResourceAsStream("mybatis/mybatis-configuration.xml"));
        session = factory.openSession();
    }

    @Test
    public void mapperClient() throws Exception {
        UserDAOWithMapper dao = session.getMapper(UserDAOWithMapper.class);
        User user = dao.selectUserById(1);
        System.out.println(user);
    }
    @Test
    public void updateClient() throws Exception {
        UserDAOWithMapper dao = session.getMapper(UserDAOWithMapper.class);
        dao.updateUserById(2, "Sherry", "987654321");
//        System.out.println(user);
    }
    @Test
    public void selectByName() throws Exception {
        UserDAOWithMapper dao = session.getMapper(UserDAOWithMapper.class);
        User user = new User();
        user.setId(1);
        user.setName("Aires");
        user.setPassword("958712");
        User user1=dao.selectUserByName(user);
        System.out.println(user1);
//        return user1;
    }

    @Test
    public void selectUserByMap() throws Exception {
        UserDAOWithMapper dao = session.getMapper(UserDAOWithMapper.class);
        Map<String, Object> map  = new HashMap<String, Object>();
        map.put("name", "Aires");
        map.put("password", "ICy5YqxZB1uWSwcVLSNLcA==");

        User user1=dao.selectUserByMap(map);

        System.out.println(user1);
//        return user1;
    }
  @Test
    public void selectUserMapByName() throws Exception {
        UserDAOWithMapper dao = session.getMapper(UserDAOWithMapper.class);


        User user=dao.selectUserMapByName("Aires");

        System.out.println(user);
//        return user1;
    }

    @After
    public void tearDown() {
        session.close();
    }
}
