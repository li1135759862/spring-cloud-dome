package com.component;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@SpringBootConfiguration
@MapperScan(basePackages = "com.cloud",sqlSessionFactoryRef = "cloudMysqlSessionFactory")
public class CloudMysqlConfig {


    @Bean
    public SqlSessionFactory cloudMysqlSessionFactory(@Qualifier("cloud") DataSource mysqlDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(mysqlDataSource);
        return bean.getObject();
    }

    /*事物模板*/
    @Bean("cloudTransaction")
    public PlatformTransactionManager cloudTransactionManager(@Qualifier("cloud")DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
