package com.aires.mybatis.dao;

import com.aires.mybatis.po.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by 10183966 on 2017/2/17.
 */
public class UserDAOWithOutDao {
    private SqlSessionFactory factory;

    @Before
    public void setUp() throws IOException {
        String resource = "mybatis/mybatis-configuration.xml";
        factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(resource));
    }

    @Test
    public void selectUserById() {
        try (SqlSession session = factory.openSession()) {
            User user = session.selectOne("namespace.selectUserById", 1);
            System.out.println(user);
        }
    }

    @Test
    public void selectUserByName() {
        try (SqlSession session = factory.openSession()) {
            List<User> users = session.selectList("namespace.selectUserByName", "Sherry");
            for (User user : users) {
                System.out.println(user);
            }
        }
    }

    @Test
    public void insertUser() {
        try (SqlSession session = factory.openSession()) {
            User user = new User();
            user.setId(3);
            user.setName("new_name_3");
            user.setPassword("new_password_3");
            session.insert("namespace.insertUser", user);
            session.commit();

            System.out.println(user.getId());
        }
    }

    @Test
    public void updateUserById() {
        try (SqlSession session = factory.openSession(true)) {
            session.update("namespace.updateUserById",
                    new User(1, "Aires", "ICy5YqxZB1uWSwcVLSNLcA=="));
        }
    }

    @Test
    public void deleteUserById() {
        try (SqlSession session = factory.openSession(true)) {
            session.delete("namespace.deleteUserById", 3);
        }
    }
}
