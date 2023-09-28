package com.hypeboy.hypeBoard.config;

import com.hypeboy.hypeBoard.connectionpool.ConnectionPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Bean
    @Scope("singleton")
    public ConnectionPool connectionPool(){
        return new ConnectionPool(dataSource());
    }
    public DataSource dataSource(){
        return DataSourceBuilder.create()
                .url(dataSourceUrl)
                .username(username)
                .password(password)
                .build();
    }
}
