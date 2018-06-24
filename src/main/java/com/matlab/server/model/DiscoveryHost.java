package com.matlab.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
@Document
public class DiscoveryHost {

//    @JsonProperty("ip_address")
//    private String ipAddress;
//    private String lastCheckIn;
//    private Integer port;
    @Id
    private CompositeKey key;
//    private String id;

    private String ip;
    private Integer port;
    private Integer sizeMatrix;
    private Double timeMatrix;
    private boolean matlabExists;
    private boolean gpuExists;
    private boolean lock;
//    private String revision;
//    private String service;
//    @JsonProperty("service_repo_name")
//    private String serviceRepoName;

//    public DiscoveryHost(CompositeKey key) {
//        this.key = key;
//    }
//    public DiscoveryHost(CompositeKey key, Integer sizeMatrix, Integer timeMatrix) {
//        this(key);
//        this.sizeMatrix = sizeMatrix;
//        this.timeMatrix = timeMatrix;
//    }

    @JsonCreator
    public DiscoveryHost(
            @JsonProperty("ip") String ip,
            @JsonProperty("port") Integer port,
            @JsonProperty("sizeMatrix") Integer sizeMatrix,
            @JsonProperty("timeMatrix") Double timeMatrix,
            @JsonProperty("matlabExists") boolean matlabExists,
            @JsonProperty("gpuExists") boolean gpuExists,
            @JsonProperty("lock") boolean lock) {
        this.key = new CompositeKey(ip, port);
        this.ip = ip;
        this.port = port;
        this.sizeMatrix = sizeMatrix;
        this.timeMatrix = timeMatrix;
        this.matlabExists = matlabExists;
        this.gpuExists = gpuExists;
        this.lock = lock;
    }

    public DiscoveryHost() {
    }

//    public DiscoveryHost(String ip, Integer port) {
//        this.key = new CompositeKey(ip, port);
//    }

    @Override
    public String toString() {
        return key.toString();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CompositeKey implements Serializable {
        private String ipAddress;
        private Integer port;

        @Override
        public String toString() {
            return "DiscoveryHost [ipAddress=" + ipAddress + ", port=" + port + "]";
        }
    }
}
