package com.biat.Interfacage_ov.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Configuration
public class DataSourceGlobal {


    @Autowired
    @Qualifier("JdbcTemplateenv")
    JdbcTemplate jdbcTemplateenvtrac;

    @Bean(name = "tracanomalieDb")
    public DataSource mysqlDataSourceanomalie() {

        DataSourceBuilder dataSourceanomalie = DataSourceBuilder.create();
        List<Map<String, Object>> list = jdbcTemplateenvtrac.queryForList("SELECT * FROM ov_ref WHERE ACTIVITIE='1' AND ENV='tracanomaliet24'");
        dataSourceanomalie.driverClassName((String) list.get(0).get("DRIVER"));
        dataSourceanomalie.url((String) list.get(0).get("URL"));
        dataSourceanomalie.username((String) list.get(0).get("USERNAME"));
        dataSourceanomalie.password((String) list.get(0).get("PASSWORD"));
        return dataSourceanomalie.build();
    }

    @Bean(name = "JdbcTemplatracanomalie")
    public JdbcTemplate jdbcTemplateanomalie(@Qualifier("tracanomalieDb") DataSource datasourceanomalie) {
        return new JdbcTemplate(datasourceanomalie);
    }
    @Bean(name = "oracleDb")
    public DataSource mysqlDataSourceoracleBat() {

        DataSourceBuilder dataSourceOracle = DataSourceBuilder.create();
        List<Map<String, Object>> list = jdbcTemplateenvtrac.queryForList("SELECT * FROM ov_ref WHERE ACTIVITIE='1' AND ENV='ORACLEBAT'");
        dataSourceOracle.driverClassName((String) list.get(0).get("DRIVER"));
        dataSourceOracle.url((String) list.get(0).get("URL"));
        dataSourceOracle.username((String) list.get(0).get("USERNAME"));
        dataSourceOracle.password((String) list.get(0).get("PASSWORD"));
        return dataSourceOracle.build();
    }

    @Bean(name = "JdbcTemplateoracle")
    public JdbcTemplate jdbcTemplateoraclebat(@Qualifier("oracleDb") DataSource datasourceoraclebat) {
        return new JdbcTemplate(datasourceoraclebat);
    }
}
