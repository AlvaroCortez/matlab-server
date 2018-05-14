package com.matlab.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscoveryHost {

    @JsonProperty("ip_address")
    private String ipAddress;
    private String lastCheckIn;
    private Integer port;
    private String revision;
    private String service;
    @JsonProperty("service_repo_name")
    private String serviceRepoName;

    @Override
    public String toString() {
        return "DiscoveryHost [ipAddress=" + ipAddress + ", port=" + port + "]";
    }
}
