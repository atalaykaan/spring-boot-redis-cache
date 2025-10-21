package com.atalaykaan.spring_boot_redis_cache.repository;

import com.atalaykaan.spring_boot_redis_cache.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, Long> {
}
