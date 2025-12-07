package com.design.urlshortener.config;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemcachedConfig {

    @Value("${memcached.server}")
    private String server;

    @Value("${memcached.port}")
    private int port;

    @Value("${memcached.pool:5}")
    private int poolSize;

    @Bean
    public MemcachedClient memcachedClient() throws Exception {
        String address = server + ":" + port;
        XMemcachedClientBuilder builder = new XMemcachedClientBuilder(address);

        builder.setConnectionPoolSize(poolSize);

        builder.setConnectTimeout(2000);
        builder.setOpTimeout(3000);

        return builder.build();
    }
}