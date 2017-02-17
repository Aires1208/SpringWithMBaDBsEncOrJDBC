package com.aires.databasesource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by 10183966 on 2017/2/17.
 */
public class JdbcRowSetClient {
    private JdbcRowSet set;

    @Before
    public void setUp() throws IOException, SQLException, ClassNotFoundException {
        Properties config = SQLUtil.loadConfig("common.properties");

        Class.forName(config.getProperty("mysql.driver.class"));

        set = RowSetProvider.newFactory().createJdbcRowSet();
        set.setUrl(config.getProperty("mysql.url"));
        set.setUsername(config.getProperty("mysql.user"));
        set.setPassword(config.getProperty("mysql.password"));
    }

    @Test
    public void select() throws SQLException {
        set.setCommand("select * from city");
        set.execute();

        // 反向迭代
        set.afterLast();
        while (set.previous()) {
            System.out.printf("%d\t%s\t%s%n", set.getInt(1), set.getString(2), set.getString(3));
            if (set.getInt(1) == 187) {
                set.updateString("SH", "BJ");
                set.updateRow();
            }
        }
    }

    @After
    public void tearDown() {
        try {
            set.close();
        } catch (SQLException e) {
        }
    }
}
