package com.matlab.server.repository;

import com.matlab.server.model.DiscoveryHost;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DiscoveryHostRepository extends MongoRepository<DiscoveryHost, DiscoveryHost.CompositeKey> {

    DiscoveryHost findByIpAndPort(String ip, Integer port);
}
