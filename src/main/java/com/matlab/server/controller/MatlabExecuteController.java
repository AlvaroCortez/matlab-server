package com.matlab.server.controller;

import com.matlab.server.model.DiscoveryHosts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MatlabExecuteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatlabExecuteController.class);

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/v1/execute/{serviceName}")
    public DiscoveryHosts executeByServiceName(@PathVariable("serviceName") String serviceName) {
        LOGGER.info("execute: service={}", serviceName);
        return null;
    }
}
