package com.aires.databasesource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 10183966 on 2017/2/17.
 */
public class BatchTest {
    private Connection connection = null;
    private Random random = new Random();

    @Before
    public void setUp() {
        connection = ConnectionManger.getConnectionHikari("common.properties");
    }

    @Test
    public void updateBatch() throws SQLException {
        List<String> sqlList = new ArrayList<String>();
        for (int i = 5; i < 11; ++i) {
            String sql = "INSERT INTO city(id,name, province) VALUES("+i+" ,'city:"+i+"','" + encodeByMd5(random.nextInt() + "") + "');";

            System.out.println(sql);

            sqlList.add(sql);
        }
        int[] results = update(connection, sqlList);
        for (int result : results) {
            System.out.printf("%d ", result);
        }
    }

    private int[] update(Connection connection, List<String> sqlList) {

        boolean autoCommitFlag = false;
        try {
            autoCommitFlag = connection.getAutoCommit();

            // 关闭自动提交, 打开事务
            connection.setAutoCommit(false);

            // 收集SQL语句
            Statement statement = connection.createStatement();
            for (String sql : sqlList) {
                statement.addBatch(sql);
            }

            // 批量执行 & 提交事务
            int[] result = statement.executeBatch();
            connection.commit();

            return result;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignored) {
            }
            throw new RuntimeException(e);
        } finally {
            try {
                connection.setAutoCommit(autoCommitFlag);
            } catch (SQLException ignored) {
            }
        }
    }

    private String encodeByMd5(String input) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64Encoder = new BASE64Encoder();
            return base64Encoder.encode(md5.digest(input.getBytes("utf-8")));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void tearDown() {
        try {
            connection.close();
        } catch (SQLException ignored) {
        }
    }
}
