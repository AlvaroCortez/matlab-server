package com.matlab.server.configuration;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Bean
    public MongoClient mongo() {
        return new MongoClient();
    }

    @Bean
    protected MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongo(), "matlab-servers");
    }
}
