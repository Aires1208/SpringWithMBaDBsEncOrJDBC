package com.aires.databasesource;

import org.junit.Test;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by 10183966 on 2017/2/17.
 */
public class CachedRowSetClient {
    private CachedRowSet query(String config, String sql) {


        /*Connection/Statement/ResultSet会自动关闭*/
        try (
                Connection connection = ConnectionManger.getConnectionHikari(config);
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)
        ) {
            CachedRowSet rowSet = RowSetProvider.newFactory().createCachedRowSet();
            rowSet.populate(rs);
            return rowSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void client() throws SQLException {
        CachedRowSet set = query("common.properties", "select * from city");

        // 此时RowSet已离线
        while (set.next()) {
            System.out.printf("%s\t%s\t%s%n", set.getInt(1), set.getString(2), set.getString(3));
            if (set.getInt(1) == 1) {
                set.updateString(1, "3");
                set.updateRow();
            }
        }

        // 重新获得连接
        Connection connection = ConnectionManger.getConnectionHikari("common.properties");
        connection.setAutoCommit(false);
        // 把对RowSet所做的修改同步到数据库
        set.acceptChanges(connection);
    }

    @Test
    public void cachedRowSetPaging() throws SQLException {

        int page = 4;
        int size = 10;

        try (
                ResultSet rs = ConnectionManger.getConnectionHikari("common.properties")
                        .createStatement()
                        .executeQuery("SELECT * FROM city ORDER BY id")
        ) {
            CachedRowSet rowSet = RowSetProvider.newFactory().createCachedRowSet();
            rowSet.populate(rs, (page - 1) * size + 1);
            rowSet.setPageSize(size);

            while (rowSet.nextPage()) {
                rowSet.next();
                SQLUtil.displayResultSet(rowSet, 1);
            }
        }
    }
}
