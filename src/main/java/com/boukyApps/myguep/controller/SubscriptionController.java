package com.boukyApps.myguep.controller;

import com.boukyApps.myguep.service.DataSourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
public class SubscriptionController {

    private final DataSourceService dataSourceService;

    public SubscriptionController(DataSourceService dataSourceService) {
        this.dataSourceService = dataSourceService;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestParam int tenantId) {
        dataSourceService.addDataSource(tenantId);
        return ResponseEntity.ok("Subscribed to tenant " + tenantId);
    }

    @GetMapping("/subscribe")
    public ResponseEntity<?> getSubscribe(@RequestParam int tenantId) {
        DataSource dataSource = dataSourceService.getDataSource(tenantId);
        return ResponseEntity.ok("Subscribed to tenant " + dataSource);
    }
}