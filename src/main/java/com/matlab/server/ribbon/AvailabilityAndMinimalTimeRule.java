package com.matlab.server.ribbon;

import com.matlab.server.model.DiscoveryHost;
import com.matlab.server.repository.DiscoveryHostRepository;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AvailabilityAndMinimalTimeRule extends AbstractLoadBalancerRule {

    private static Logger log = LoggerFactory.getLogger(AvailabilityAndMinimalTimeRule.class);

    private LoadBalancerStats loadBalancerStats;

    @Autowired
    private DiscoveryHostRepository repository;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        ILoadBalancer lb = getLoadBalancer();
        if (lb == null) {
            log.warn("no load balancer");
            return null;
        }

        repository.findAll().forEach(System.out::println);

        Server chosenServer = null;
        int count = 0;
        while (chosenServer == null && count++ < 10) {
            List<Server> reachableServers = lb.getReachableServers();
            List<Server> allServers = lb.getAllServers();
            int upCount = reachableServers.size();
            int serverCount = allServers.size();

            if ((upCount == 0) || (serverCount == 0)) {
                log.warn("No up servers available from load balancer: " + lb);
                return null;
            }

            if (allServers.size() == 1) {
                chosenServer = allServers.get(0);
                DiscoveryHost host = repository.findByIpAndPort(chosenServer.getHost(), chosenServer.getPort());
                if (host.isLock()) {
                    return null;
                }
                host.setLock(true);
                repository.save(host);
                return allServers.get(0);
            }

//            int nextServerIndex = 0;
//            chosenServer = allServers.get(nextServerIndex);

            if (chosenServer == null) {
                /* Transient. */
                Thread.yield();
                continue;
            }

            if (chosenServer.isAlive() && (chosenServer.isReadyToServe())) {
                return (chosenServer);
            }

            // Next.
            chosenServer = null;
        }

        if (count >= 10) {
            log.warn("No available alive servers after 10 tries from load balancer: "
                             + lb);
        }
        return null;
    }

    @Override
    public void setLoadBalancer(ILoadBalancer lb) {
        super.setLoadBalancer(lb);
        if (lb instanceof AbstractLoadBalancer) {
            loadBalancerStats = ((AbstractLoadBalancer) lb).getLoadBalancerStats();
        }
    }
}
