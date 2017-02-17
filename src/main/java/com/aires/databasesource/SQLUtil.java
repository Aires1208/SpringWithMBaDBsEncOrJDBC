package com.aires.databasesource;

import javax.sql.RowSet;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class SQLUtil {

    public static void executeSQL(Statement statement, String sql) {
        try {
            // 如果含有ResultSet
            if (statement.execute(sql)) {
                ResultSet rs = statement.getResultSet();
                ResultSetMetaData meta = rs.getMetaData();
                int columnCount = meta.getColumnCount();
                for (int i = 1; i <= columnCount; ++i) {
                    System.out.printf("%s\t", meta.getColumnName(i));
                }
                System.out.println();

                while (rs.next()) {
                    for (int i = 1; i <= columnCount; ++i) {
                        System.out.printf("%s\t", rs.getObject(i));
                    }
                    System.out.println();
                }
            } else {
                System.out.printf("该SQL语句共影响%d条记录%n", statement.getUpdateCount());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加载.properties配置文件
     *
     * @param file
     * @return
     */
    public static Properties loadConfig(String file) {

        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream(file));
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static RowSet initRowSet(RowSet set, Properties config) {
        try {
            Class.forName(config.getProperty("mysql.driver.class"));
            set.setUrl(config.getProperty("mysql.url"));
            set.setUsername(config.getProperty("mysql.user"));
            set.setPassword(config.getProperty("mysql.password"));

            return set;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void displayResultSet(ResultSet result, int column) {
        try {
            result.beforeFirst();
            while (result.next()) {
                for (int i = 1; i <= column; ++i) {
                    System.out.printf("%s\t", result.getObject(i));
                }
                System.out.printf("%s%n", result.getObject(column));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
