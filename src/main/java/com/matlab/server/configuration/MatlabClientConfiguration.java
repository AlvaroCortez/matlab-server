package com.matlab.server.configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class MatlabClientConfiguration {

    @Autowired
    private IClientConfig ribbonClientConfig;

//    @Bean
//    public IPing ribbonPing(IClientConfig config) {
//        return new PingUrl();
//    }

    @Bean
    public IRule ribbonRule(IClientConfig config) {
//        return new AvailabilityFilteringRule();
        CustomRibbonRule rule = new CustomRibbonRule();
        rule.initWithNiwsConfig(ribbonClientConfig);
        return rule;
    }
}
