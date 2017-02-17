package com.aires.databasesource.client;

import com.aires.mybatis.dao.UserDAOWithMapper;
import com.aires.mybatis.po.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by 10183966 on 2017/2/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class UserDAOClient {

    @Autowired
    private UserDAOWithMapper dao;


//    private SqlSessionFactory factory;
//
//    @Before
//    public void setUp() {
//        factory = new SqlSessionFactoryBuilder().
//                build(ClassLoader.getSystemResourceAsStream("mybatis/mybatis-configuration.xml"));
////      SqlSession  session = factory.openSession();
//    }

    @Test
    public void client() throws Exception {
//        User user = dao.selectUserById(1);
        testCache();
        testCache();
        testCache();
    }

//    @Test
//    public void cacheClient() throws Exception {
//        testCache(factory.openSession());
//        testCache(factory.openSession());
//        testCache(factory.openSession());
//    }

    private void testCache() throws Exception {
//        UserDAOWithMapper dao = session.getMapper(UserDAOWithMapper.class);
        User user = dao.selectUserById(1);
        System.out.println(user);

        // 需要将SqlSession关闭才能将数据写入缓存.
//        session.close();
    }

    @Test
    public void selectUserClient() throws Exception {
        User userIn = new User();
        userIn.setId(null);
        userIn.setName(null);
        userIn.setPassword("new_password");
        List<User> userList=dao.selectUser(userIn);
        userList.forEach(user -> System.out.println(user));
//        System.out.println(user);
    }
}
