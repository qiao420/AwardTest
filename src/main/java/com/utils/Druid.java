package com.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Druid {
    private static DruidDataSource dataSource = null;

    public static void init() throws Exception {
        Properties properties = new Properties();
        InputStream in = Druid.class.getClassLoader().getResourceAsStream("druid.properties");
        properties.load(in);
        dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        in.close();
    }

    public static Connection getConnection() throws Exception {
        if(null == dataSource)
        {
            init();
        }
        return dataSource.getConnection();
    }
    public static void close(Connection conn) throws SQLException {
        if(conn!=null){
            conn.close();
        }
    }
}
