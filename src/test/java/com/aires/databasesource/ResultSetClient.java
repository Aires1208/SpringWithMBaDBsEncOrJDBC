package com.aires.databasesource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 10183966 on 2017/2/17.
 */
public class ResultSetClient {
    private Connection connection = null;

    @Before
    public void setUp() {
        connection = ConnectionManger.getConnectionHikari("common.properties");
    }

    @Test
    public void updateResultSet() throws SQLException {
        // 创建可更新,底层数据敏感的Statement
        try (
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM city where id IN(?, ?)",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)
        ) {
            statement.setInt(1, 19);
            statement.setInt(2, 89);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                System.out.printf("%s\t%s\t%s\t%n", rs.getInt(1), rs.getString(2), rs.getString(3));
                if (rs.getString("name").equals("SH")) {
                    rs.updateString("name", "BJ");
                    rs.updateRow();
                }
            }
            SQLUtil.displayResultSet(rs, 0);
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
