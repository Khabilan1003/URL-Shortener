package com.design.urlshortener.service;

public interface MemcachedService {
    public void set(String key, Object value, int ttlSeconds) throws Exception;

    public Object get(String key) throws Exception;

    public void delete(String key) throws Exception;
}
