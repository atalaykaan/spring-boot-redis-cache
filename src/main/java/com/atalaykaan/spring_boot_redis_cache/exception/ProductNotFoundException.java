package com.atalaykaan.spring_boot_redis_cache.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {

        super(message);
    }
}
