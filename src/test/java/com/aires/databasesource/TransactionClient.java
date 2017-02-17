package com.aires.databasesource;

import org.junit.After;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by 10183966 on 2017/2/17.
 */
public class TransactionClient {

    private Connection connection = ConnectionManger.getConnectionHikari("common.properties");


    @Test
    public void noTransaction() throws SQLException {
        try (
                PreparedStatement minusSM = connection.prepareStatement("UPDATE `account` SET `money`=(`money` - ?) WHERE `name`=?");
                PreparedStatement addSM = connection.prepareStatement("UPDATE `account` SET `money`=(`money` + ?) WHERE `name`=?")
        ) {
            // 从feiqing账户转出
            minusSM.setBigDecimal(1, new BigDecimal(100));
            minusSM.setString(2, "feiqing");
            minusSM.execute();

            // 中途抛出异常, 会导致两账户前后不一致
            if (true){
                throw new RuntimeException("no-transaction");
            }

            // 转入Aires账户
            addSM.setBigDecimal(1, new BigDecimal(100));
            addSM.setString(2, "Aires");
            addSM.execute();
        }
    }
    @Test
    public void byTransaction() throws SQLException {

        boolean autoCommitFlag = connection.getAutoCommit();
        // 关闭自动提交, 开启事务
        connection.setAutoCommit(false);

        try (
                PreparedStatement minusSM = connection.prepareStatement("UPDATE `account` SET `money`=(`money` - ?) WHERE `name`=?");
                PreparedStatement addSM = connection.prepareStatement("UPDATE `account` SET `money`=(`money` + ?) WHERE `name`=?")
        ) {
            // 从feiqing账户转出
            minusSM.setBigDecimal(1, new BigDecimal(100));
            minusSM.setString(2, "feiqing");
            minusSM.execute();

            // 中途抛出异常: rollback
            if (true) {
                throw new RuntimeException("no-transaction");
            }

            // 转入Aires账户
            addSM.setBigDecimal(1, new BigDecimal(100));
            addSM.setString(2, "Aires");
            addSM.execute();
            connection.commit();
        } catch (Throwable e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(autoCommitFlag);
        }
    }

    @After
    public void tearDown() {
        try {
            connection.close();
        } catch (SQLException e) {
        }
    }
}
