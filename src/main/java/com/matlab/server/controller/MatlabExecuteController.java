package com.matlab.server.controller;

import com.matlab.server.configuration.MatlabClientConfiguration;
import com.matlab.server.model.DiscoveryHost;
import com.matlab.server.model.RequestWrapper;
import com.matlab.server.model.ResponseWrapper;
import com.matlab.server.repository.DiscoveryHostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RibbonClient(name = "matlab-client", configuration = MatlabClientConfiguration.class)
public class MatlabExecuteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatlabExecuteController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private DiscoveryHostRepository repository;

//    @GetMapping(value = "/showAllServiceIds")
//    public String showAllServiceIds() {
//
//        List<String> serviceIds = this.discoveryClient.getServices();
//
//        if (serviceIds == null || serviceIds.isEmpty()) {
//            return "No services found!";
//        }
//        String html = "<h3>Service Ids:</h3>";
//        for (String serviceId : serviceIds) {
//            html += "<br><a href='showService/" + serviceId + "'>" + serviceId + "</a>";
//        }
//        return html;
//    }
//
//    @GetMapping(value = "/showService/{serviceId}")
//    public String showFirstService(@PathVariable String serviceId) {
//
//        // (Need!!) eureka.client.fetchRegistry=true
//        List<ServiceInstance> instances = this.discoveryClient.getInstances(serviceId);
//
//        if (instances == null || instances.isEmpty()) {
//            return "No instances for service: " + serviceId;
//        }
//        String html = "<h2>Instances for Service Id: " + serviceId + "</h2>";
//
//        for (ServiceInstance serviceInstance : instances) {
//            html += "<h3>Instance: " + serviceInstance.getUri() + "</h3>";
//            html += "Host: " + serviceInstance.getHost() + "<br>";
//            html += "Port: " + serviceInstance.getPort() + "<br>";
//        }
//
//        return html;
//    }

    @PostMapping("/v1/matlab-client/execute")
    public ResponseWrapper testExecuteMatlabClient(@RequestBody RequestWrapper wrapper) {
        LOGGER.info("/v1/matlab-client/execute");
        String serviceId = "matlab-client";

        // (Need!!) eureka.client.fetchRegistry=true
        List<ServiceInstance> instances = this.discoveryClient.getInstances(serviceId);

        if (instances == null || instances.isEmpty()) {
            return new ResponseWrapper(null, null, "No instances for service: " + serviceId);
//            return "No instances for service: " + serviceId;
        }
//        String html = "<h2>Instances for Service Id: " + serviceId + "</h2>";
        String html = "";

//        for (ServiceInstance serviceInstance : instances) {
//            html += "<h3>Instance :" + serviceInstance.getUri() + "</h3>";
//        }
        ServiceInstance serviceInstance = this.loadBalancer.choose(serviceId);
        if (serviceInstance == null) {
            return new ResponseWrapper(null, null, "No instances for service: " + serviceId);
//            return "No instances for service: " + serviceId;
        }
//        html += "<br>===> Load Balancer choose: " + serviceInstance.getUri();
        html += "Execute on: " + serviceInstance.getUri();

//        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort();

//        html += "<br>Make a Call: " + url;
//        html += "<br>";
//        String greeting = this.restTemplate.getForObject("http://matlab-client/greeting", String.class);
        String greeting = this.restTemplate.getForObject("http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/v1/matlab", String.class);
//        String greeting = this.restTemplate.getForObject("http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/files/result", String.class);
        html += "<br>Result: " + greeting;
        DiscoveryHost host = repository.findByIpAndPort(serviceInstance.getHost(), serviceInstance.getPort());
        if (host.isLock()) {
            host.setLock(false);
            repository.save(host);
        }
        return new ResponseWrapper(serviceInstance.getUri().toString(), greeting, "");
//        return html;
    }
}
