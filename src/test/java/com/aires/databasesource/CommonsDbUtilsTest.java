package com.aires.databasesource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

/**
 * Created by 10183966 on 2017/2/17.
 */
public class CommonsDbUtilsTest {
    /*
    * int update(String sql, Object... params);
      int update(Connection conn, String sql, Object... params);
    * */
    @Test
    public void update() throws SQLException {
        QueryRunner runner = new QueryRunner(ConnectionManger.getDataSourceHikari("common.properties"));
        String sql = "INSERT INTO city(id,name, province) VALUES(?,?, ?)";
        runner.update(sql,100, "BJ", "001");
    }

    /*
    * <T> T query(String sql, ResultSetHandler<T> rsh, Object... params);
      <T> T query(Connection conn, String sql, ResultSetHandler<T> rsh, Object... params);

      本例中, 使用自定的ResultSetHandler将ResultSet转换成JavaBean,
      但实际上dbutils默认已经提供了很多定义良好的Handler实现:

    BeanHandler : 单行处理器,将ResultSet转换成JavaBean;
    BeanListHandler : 多行处理器,将ResultSet转换成List<JavaBean>;
    MapHandler : 单行处理器,将ResultSet转换成Map<String,Object>, 列名为键;
    MapListHandler : 多行处理器,将ResultSet转换成List<Map<String,Object>>;
    ScalarHandler : 单行单列处理器,将ResultSet转换成Object(如保存SELECT COUNT(*) FROM t_ddl).
    ColumnListHandler : 多行单列处理器,将ResultSet转换成List<Object>(使用时需要指定某一列的名称/编号,如new ColumListHandler(“name”):表示把name列数据放到List中);

    * */

    @Test
    public void select() throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "SELECT * FROM city WHERE id = ?";
        TDDL result = runner.query(ConnectionManger.getConnectionHikari("common.properties"), sql, rsh, 7);
        System.out.println(result);
    }

    private ResultSetHandler<TDDL> rsh = new ResultSetHandler<TDDL>() {

        @Override
        public TDDL handle(ResultSet rs) throws SQLException {
            TDDL tddl = new TDDL();
            if (rs.next()) {
                tddl.setId(rs.getInt(1));
                tddl.setName(rs.getString(2));
                tddl.setProvince(rs.getString(3));
            }
            return tddl;
        }
    };



    @Test
    public void clientBeanHandler() throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "SELECT * FROM city WHERE id = ?";
        TDDL result = runner.query(ConnectionManger.getConnectionHikari("common.properties"),sql, new BeanHandler<>(TDDL.class), 7);
        System.out.println(result);
    }

    @Test
    public void clientBeanListHandler() throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "SELECT * FROM city";
        List<TDDL> result = runner.query(ConnectionManger.getConnectionHikari("common.properties"),sql, new BeanListHandler<>(TDDL.class));
        System.out.println(result);
    }

    @Test
    public void clientScalarHandler() throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "SELECT COUNT(*) FROM city";
        Long result = runner.query(ConnectionManger.getConnectionHikari("common.properties"),sql, new ScalarHandler<Long>());
        System.out.println(result);
    }

    @Test
    public void clientColumnListHandler() throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "SELECT * FROM city";
        List<String> query = runner.query(ConnectionManger.getConnectionHikari("common.properties"),sql, new ColumnListHandler<String>("name"));
        for (String i : query) {
            System.out.printf("%n%s", i);
        }
    }

    @Test
    public void clientBath() throws SQLException {
        QueryRunner runner = new QueryRunner();
        Random random = new Random();
        String sql =  "INSERT INTO city(id,name, province) VALUES(?,?, ?)";
        int count = 46;
        Object[][] params = new Object[count][];
        for (int i = 0; i < count; ++i) {
            params[i] = new Object[]{i,"city-" + i, "name-" + random.nextInt()};
            System.out.println( params[i].toString());
        }

        runner.batch(ConnectionManger.getConnectionHikari("common.properties"),sql, params);
    }
}
