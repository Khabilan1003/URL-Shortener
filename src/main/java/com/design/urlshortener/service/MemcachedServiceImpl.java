package com.design.urlshortener.service;

import net.rubyeye.xmemcached.MemcachedClient;
import org.springframework.stereotype.Service;

@Service
public class MemcachedServiceImpl implements MemcachedService {

    private final MemcachedClient client;

    public MemcachedServiceImpl(MemcachedClient client) {
        this.client = client;
    }

    public void set(String key, Object value, int ttlSeconds) throws Exception {
        client.set(key, ttlSeconds, value);
    }

    public Object get(String key) throws Exception {
        return client.get(key);
    }

    public void delete(String key) throws Exception {
        client.delete(key);
    }
}