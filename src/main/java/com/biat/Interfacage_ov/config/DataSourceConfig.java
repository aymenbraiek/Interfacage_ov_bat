package com.biat.Interfacage_ov.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DataSourceConfig {
    @Bean(name = "tracenvDb")
    @Primary
    @ConfigurationProperties(prefix = "app.datasource.first")
    public DataSource mysqlDataSourceanomalie() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "JdbcTemplateenv")
    public JdbcTemplate jdbcTemplateanomalie(@Qualifier("tracenvDb") DataSource datasourcetracenv) {
        return new JdbcTemplate(datasourcetracenv);
    }


}
