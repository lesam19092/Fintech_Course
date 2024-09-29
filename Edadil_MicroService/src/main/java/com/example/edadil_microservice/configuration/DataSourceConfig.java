package com.example.edadil_microservice.configuration;


import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {
    private String postgres = "postgres";

    @Bean
    public DataSource myPostgresDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/edadil_db");
        ds.setUsername(postgres);
        ds.setPassword(postgres);
        return ds;
    }
}