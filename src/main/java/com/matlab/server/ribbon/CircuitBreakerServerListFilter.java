package com.matlab.server.ribbon;

import com.netflix.loadbalancer.AbstractServerListFilter;
import com.netflix.loadbalancer.LoadBalancerStats;
import com.netflix.loadbalancer.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class CircuitBreakerServerListFilter extends AbstractServerListFilter<Server> {

    private static Logger log = LoggerFactory.getLogger(CircuitBreakerServerListFilter.class);

    @Override
    public List<Server> getFilteredListOfServers(List<Server> servers) {
        LoadBalancerStats stats = getLoadBalancerStats();
        if (stats == null) {
            return servers;
        }
        stats.getSingleServerStat(servers.get(0)).getActiveRequestsCount();
        return servers.stream().
                filter(server -> !stats.getSingleServerStat(server).isCircuitBreakerTripped())
                .collect(Collectors.toList());
    }
}
