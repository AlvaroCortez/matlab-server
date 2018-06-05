package com.matlab.server.controller;

import com.matlab.server.model.DiscoveryHost;
import com.matlab.server.repository.DiscoveryHostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class MatlabClientRegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatlabClientRegistrationController.class);

    @Autowired
    private DiscoveryHostRepository repository;

    @Autowired
    private RestTemplate restTemplate;

//    @GetMapping(value = "/v1/registration/{serviceName}")
//    public DiscoveryHosts getHostsByServiceName(@PathVariable("serviceName") String serviceName) {
//        LOGGER.info("getHostsByServiceName: service={}", serviceName);
//
//        LOGGER.info("getHostsByServiceName: hosts={}", hostsList);
//        return hostsList;
//    }

    @PostMapping("/v1/registration/{serviceName}")
    public void addHost(@PathVariable("serviceName") String serviceName, @RequestBody DiscoveryHost host) {
        LOGGER.info("addHost: service={}, body={}", serviceName, host);
        repository.save(host);
    }

    @DeleteMapping("/v1/registration/{serviceName}/{ipAddress}/{port}")
    public void deleteHost(@PathVariable("serviceName") String serviceName,
                           @PathVariable("ipAddress") String ipAddress,
                           @PathVariable("port") Integer port) {
        LOGGER.info("deleteHost: service={}, ip={}", serviceName, ipAddress);
        repository.delete(repository.findByIpAndPort(ipAddress, port));
    }
}
