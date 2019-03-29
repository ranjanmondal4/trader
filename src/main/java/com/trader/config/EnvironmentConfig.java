package com.trader.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class EnvironmentConfig {

    @Value("#{'${spring.kafka.bootstrap-servers}'.split(',')}")
    private List<String> bootstrapServers;

    public List<String> getBootstrapServers() {
        return bootstrapServers;
    }
}
