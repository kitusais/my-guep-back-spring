package com.boukyApps.myguep.service;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DataSourceService {

    private final Map<Integer, DataSource> dataSources = new ConcurrentHashMap<>();

    public DataSource getDataSource(int tenantId) {
        return dataSources.computeIfAbsent(tenantId, this::createDataSource);
    }

    private DataSource createDataSource(int tenantId) {
        var dsp = new DataSourceProperties();
        dsp.setPassword("pwd");
        dsp.setUsername("user");
        dsp.setUrl("jdbc:postgresql://localhost:" + (54300 + tenantId) + "/user");
        return dsp.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    public void addDataSource(int tenantId) {
        dataSources.putIfAbsent(tenantId, createDataSource(tenantId));
        // Optionally initialize the data source with schema and data
        var initializer = new ResourceDatabasePopulator(
                new ClassPathResource("schema.sql"),
                new ClassPathResource("ds" + tenantId + "-data.sql"));
        initializer.execute(dataSources.get(tenantId));
    }
}
