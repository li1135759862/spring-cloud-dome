package com.component;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@SpringBootConfiguration
@MapperScan(basePackages = "com.ccbim")
public class CCBIMMysqlConfig {

    @Bean
    @Primary
    public SqlSessionFactory ccbimMysqlSessionFactory(DataSource mysqlDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources("classpath:mybatis/mappings/ccbim/*.xml"));
        bean.setDataSource(mysqlDataSource);
        return bean.getObject();
    }


    /*事物模板*/

    @Bean
    @Primary
    public PlatformTransactionManager ccbimTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
