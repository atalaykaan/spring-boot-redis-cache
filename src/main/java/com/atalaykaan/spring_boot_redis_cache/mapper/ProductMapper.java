package com.atalaykaan.spring_boot_redis_cache.mapper;

import com.atalaykaan.spring_boot_redis_cache.dto.ProductDTO;
import com.atalaykaan.spring_boot_redis_cache.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDto(Product product);

    Product toProduct(ProductDTO productDTO);
}
