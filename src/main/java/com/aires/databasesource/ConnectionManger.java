package com.aires.databasesource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionManger {

    /*双重检测锁保证DataSource单例*/
    private static DataSource dataSource;

    /*获取DataSource*/

    public static DataSource getDataSourceC3P0(String file) {
        if (dataSource == null) {
            synchronized (ConnectionManger.class) {
                if (dataSource == null) {
                    Properties config = SQLUtil.loadConfig(file);
                    try {
                        ComboPooledDataSource source = new ComboPooledDataSource();
                        source.setDriverClass(config.getProperty("mysql.driver.class"));
                        source.setJdbcUrl(config.getProperty("mysql.url"));
                        source.setUser(config.getProperty("mysql.user"));
                        source.setPassword(config.getProperty("mysql.password"));

                        // 设置连接池最大连接数
                        source.setMaxPoolSize(Integer.valueOf(config.getProperty("pool.max.size")));
                        // 设置连接池最小连接数
                        source.setMinPoolSize(Integer.valueOf(config.getProperty("pool.min.size")));
                        // 设置连接池初始连接数
                        source.setInitialPoolSize(Integer.valueOf(config.getProperty("pool.init.size")));
                        // 设置连接每次增量
                        source.setAcquireIncrement(Integer.valueOf(config.getProperty("pool.acquire.increment")));
                        // 设置连接池的缓存Statement的最大数
                        source.setMaxStatements(Integer.valueOf(config.getProperty("pool.max.statements")));
                        // 设置最大空闲时间
                        source.setMaxIdleTime(Integer.valueOf(config.getProperty("pool.max.idle_time")));

                        dataSource = source;
                    } catch (PropertyVetoException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return dataSource;
    }

    public static DataSource getDataSourceDBCP(String file) {
        if (dataSource == null) {
            synchronized (ConnectionManger.class) {
                if (dataSource == null) {
                    Properties config = SQLUtil.loadConfig(file);

                        BasicDataSource source = new BasicDataSource ();
                        source.setDriverClassName(config.getProperty("mysql.driver.class"));
                        source.setUrl(config.getProperty("mysql.url"));
                        source.setUsername(config.getProperty("mysql.user"));
                        source.setPassword(config.getProperty("mysql.password"));

                        // 设置连接池最大连接数
                        source.setMaxIdle(Integer.valueOf(config.getProperty("pool.max.size")));
                        // 设置连接池最小连接数
                        source.setMaxIdle(Integer.valueOf(config.getProperty("pool.min.size")));
                        // 设置连接池初始连接数
                        source.setInitialSize(Integer.valueOf(config.getProperty("pool.init.size")));
                        // 设置连接每次增量
//                        source.set(Integer.valueOf(config.getProperty("pool.acquire.increment")));
                        // 设置连接池的缓存Statement的最大数
                        source.setMaxOpenPreparedStatements(Integer.valueOf(config.getProperty("pool.max.statements")));
                        // 设置最大空闲时间
//                        source.set(Integer.valueOf(config.getProperty("pool.max.idle_time")));

                        dataSource = source;

                }
            }
        }
        return dataSource;
    }

    public static DataSource getDataSourceHikari(String file) {
        if (dataSource == null) {
            synchronized (ConnectionManger.class) {
                if (dataSource == null) {
                    Properties properties = SQLUtil.loadConfig(file);
                    HikariConfig config = new HikariConfig();
                    config.setDriverClassName(properties.getProperty("mysql.driver.class"));
                    config.setJdbcUrl(properties.getProperty("mysql.url"));
                    config.setUsername(properties.getProperty("mysql.user"));
                    config.setPassword(properties.getProperty("mysql.password"));

                    // 设置连接池最大连接数
                    config.setMaximumPoolSize(Integer.valueOf(properties.getProperty("pool.max.size")));
                    // 设置连接池最少连接数
                    config.setMinimumIdle(Integer.valueOf(properties.getProperty("pool.min.size")));
                    // 设置最大空闲时间
                    config.setIdleTimeout(Integer.valueOf(properties.getProperty("pool.max.idle_time")));
                    // 设置连接最长寿命
                    config.setMaxLifetime(Integer.valueOf(properties.getProperty("pool.max.life_time")));
                    dataSource = new HikariDataSource(config);
                }
            }
        }

        return dataSource;
    }

    /*获取 C3P0 Connection*/

    public static Connection getConnectionC3P0(String file) {
        return getConnection(getDataSourceC3P0(file));
    }
    /*获取 Hikari Connection*/
    public static Connection getConnectionHikari(String file) {
        return getConnection(getDataSourceHikari(file));
    }
    /*获取 DBCP Connection*/
    public static Connection getConnectionDBCP(String file) {
        return getConnection(getDataSourceDBCP(file));
    }

    public static Connection getConnection(DataSource dataSource) {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*获取原生Connection*/

    public static Connection getConnection(String file) {
        Properties config = SQLUtil.loadConfig(file);
        try {
            Class.forName(config.getProperty("mysql.driver.class"));
            String url = config.getProperty("mysql.url");
            String username = config.getProperty("mysql.user");
            String password = config.getProperty("mysql.password");
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}