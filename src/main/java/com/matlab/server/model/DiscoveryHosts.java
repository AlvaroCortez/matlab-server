package com.matlab.server.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DiscoveryHosts {

    private List<DiscoveryHost> hosts = new ArrayList<>();
    private String env;
    private String service;

    @Override
    public String toString() {
        return "DiscoveryHosts [hosts=" + hosts + "]";
    }
}
