package com.smpark.jdbc.lxp.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class JDBCConnection {

    private static final HikariDataSource dataSource;

    static {
        try {
            /*
             * Properties
             * 키 - 값을 쌍으로 저장하는 방식이다.
             * 주로 설정 정보나 구성 데이터를 관리하는데 더 유용하게 사용된다.
             * */
            Properties props = new Properties();

            /*
             * Properties.load 외부 파일을 읽어오는 역할을 수행한다.
             * */
            props.load(JDBCConnection.class.getClassLoader().getResourceAsStream("config.properties"));

            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));

            config.setMaximumPoolSize(10);

            config.setMinimumIdle(5);

            config.setIdleTimeout(30000);

            config.setMaxLifetime(1800000);

            config.setConnectionTimeout(2000);

            dataSource = new HikariDataSource(config);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close(){
        if(dataSource != null){
            dataSource.close();
        }
    }
}
