package com.matlab.server.configuration;

import com.matlab.server.ribbon.AvailabilityAndMinimalTimeRule;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class MatlabClientConfiguration {

    @Autowired
    private IClientConfig ribbonClientConfig;

    @Bean
    public IRule ribbonRule(IClientConfig config) {
        AvailabilityAndMinimalTimeRule rule = new AvailabilityAndMinimalTimeRule();
        rule.initWithNiwsConfig(ribbonClientConfig);
        return rule;
    }

//    @Bean
//    public ServerListFilter<Server> ribbonServerListFilter() {
//        return new CircuitBreakerServerListFilter();
//    }
}
