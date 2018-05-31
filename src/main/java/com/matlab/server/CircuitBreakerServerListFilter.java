package com.matlab.server;

import com.netflix.loadbalancer.AbstractServerListFilter;
import com.netflix.loadbalancer.LoadBalancerStats;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.stream.Collectors;

public class CircuitBreakerServerListFilter extends AbstractServerListFilter<Server> {

    @Override
    public List<Server> getFilteredListOfServers(List<Server> servers) {
        LoadBalancerStats stats = getLoadBalancerStats();
        if (stats == null) {
            return servers;
        }
        return servers.stream().
                filter(server -> !stats.getSingleServerStat(server).isCircuitBreakerTripped())
                .collect(Collectors.toList());
    }
}
