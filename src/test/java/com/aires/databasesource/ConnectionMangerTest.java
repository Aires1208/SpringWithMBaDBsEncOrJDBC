package com.aires.databasesource;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

/**
 * Created by 10183966 on 2017/2/16.
 */
public class ConnectionMangerTest {

    @org.junit.Test
    public void testGetConnectionC3P0() throws Exception {
        Connection connection = ConnectionManger.getConnectionC3P0("common.properties");
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM city";
//        ResultSet cities = statement.executeQuery(sql);
        SQLUtil.executeSQL(statement,sql);
//        cities.close();
        statement.close();
        connection.close();
    }

    @org.junit.Test
    public void testGetConnectionHikari() throws Exception {
        Connection connection = ConnectionManger.getConnectionHikari("common.properties");
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM city";
//        ResultSet cities = statement.executeQuery(sql);
        SQLUtil.executeSQL(statement,sql);
//        cities.close();
        statement.close();
        connection.close();
    }

    @org.junit.Test
    public void testGetConnection() throws Exception {
        Connection connection = ConnectionManger.getConnection("common.properties");
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM city";
//        ResultSet cities = statement.executeQuery(sql);
        SQLUtil.executeSQL(statement,sql);
//        cities.close();
        statement.close();
        connection.close();
    }
    @Test
    public void ddlClient() throws SQLException {
        try (
                Connection connection = ConnectionManger.getConnectionHikari("common.properties");
                Statement statement = connection.createStatement()
        ) {
            int res = statement.executeUpdate("CREATE TABLE t_ddl(" +
                    "id INT auto_increment PRIMARY KEY, " +
                    "username VARCHAR(64) NOT NULL, " +
                    "password VARCHAR (36) NOT NULL " +
                    ")");
            System.out.println(res);
        }
    }

    @Test
    public void dmlClient() throws SQLException {
        try (
                Connection connection = ConnectionManger.getConnectionHikari("common.properties");
                Statement statement = connection.createStatement()
        ) {
            int res = statement.executeUpdate("INSERT INTO " +
                    "t_ddl(username, password) " +
                    "VALUES (Sherry,23)");
            System.out.println(res);
        }
    }
}