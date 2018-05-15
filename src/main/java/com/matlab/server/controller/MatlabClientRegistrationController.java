package com.matlab.server.controller;

import com.matlab.server.model.DiscoveryHost;
import com.matlab.server.model.DiscoveryHosts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;

@RestController
public class MatlabClientRegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatlabClientRegistrationController.class);

    private Map<String, List<DiscoveryHost>> hosts = new HashMap<>();

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/v1/registration/{serviceName}")
    public DiscoveryHosts getHostsByServiceName(@PathVariable("serviceName") String serviceName) {
        LOGGER.info("getHostsByServiceName: service={}", serviceName);
        DiscoveryHosts hostsList = new DiscoveryHosts();
        hostsList.setHosts(hosts.get(serviceName));
        LOGGER.info("getHostsByServiceName: hosts={}", hostsList);
        return hostsList;
    }

    @PostMapping("/v1/registration/{serviceName}")
    public void addHost(@PathVariable("serviceName") String serviceName, @RequestBody DiscoveryHost host) {
        LOGGER.info("addHost: service={}, body={}", serviceName, host);
        List<DiscoveryHost> tmp = hosts.get(serviceName);
        if (tmp == null)
            tmp = new ArrayList<>();
        tmp.add(host);
        hosts.put(serviceName, tmp);
    }

    @DeleteMapping("/v1/registration/{serviceName}/{ipAddress}")
    public void deleteHost(@PathVariable("serviceName") String serviceName, @PathVariable("ipAddress") String ipAddress) {
        LOGGER.info("deleteHost: service={}, ip={}", serviceName, ipAddress);
        List<DiscoveryHost> tmp = hosts.get(serviceName);
        if (tmp != null) {
            Optional<DiscoveryHost> optHost = tmp.stream().filter(it -> it.getIpAddress().equals(ipAddress)).findFirst();
            optHost.ifPresent(tmp::remove);
            hosts.put(serviceName, tmp);
        }
    }

    @GetMapping(value = "/v1/execute")
    public String execute() {
        return restTemplate.getForObject("http://" + URI.create(hosts.get("matlab-client").get(0).getIpAddress() + "/v1/test"), String.class);
    }
}
