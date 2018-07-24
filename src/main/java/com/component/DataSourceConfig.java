package com.component;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    static class CompleteDataSourcesConfiguration{
        @Bean("bim360")
        @Primary
        @ConfigurationProperties(prefix="spring.datasource.primary")
        public DataSource bim360DataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
//        return DataSourceBuilder.create().build();
        }

        @Bean("cloud")
        //副数据库需要配置@Qualifier
        @Qualifier("cloud")
        @ConfigurationProperties(prefix="spring.datasource.secondary")
        public DataSource cloudDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
//        return DataSourceBuilder.create().build();
        }

        @Bean(name = "primaryJdbcTemplate")
        public JdbcTemplate primaryJdbcTemplate(
                DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean(name = "secondaryJdbcTemplate")
        public JdbcTemplate secondaryJdbcTemplate(
                @Qualifier("cloud") DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

}
