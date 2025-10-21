package com.atalaykaan.spring_boot_redis_cache.service;

import com.atalaykaan.spring_boot_redis_cache.dto.ProductDTO;
import com.atalaykaan.spring_boot_redis_cache.entity.Product;
import com.atalaykaan.spring_boot_redis_cache.exception.ProductNotFoundException;
import com.atalaykaan.spring_boot_redis_cache.mapper.ProductMapper;
import com.atalaykaan.spring_boot_redis_cache.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final SequenceGeneratorService sequenceGeneratorService;

    @Cacheable(value = "PRODUCT_CACHE", key = "#id")
    public ProductDTO findById(Long id) {

        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    @Cacheable(value = "PRODUCT_CACHE", key = "'allProducts'")
    public List<ProductDTO> findAll() {

        List<Product> products = productRepository.findAll();

        if(products.isEmpty()) {

            throw new ProductNotFoundException("No products were found");
        }

        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Caching(
            put = @CachePut(value = "PRODUCT_CACHE", key = "#result.getId()"),
            evict = @CacheEvict(value = "PRODUCT_CACHE", key = "'allProducts'")
    )
    public ProductDTO save(ProductDTO productDTO) {

        Product product = productRepository.save(Product.builder()
                .id(sequenceGeneratorService.generateSequence(Product.SEQUENCE_NAME))
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .build());

        return productMapper.toDto(product);
    }

    @Caching(
            put = @CachePut(value = "PRODUCT_CACHE", key = "#result.getId()"),
            evict = @CacheEvict(value = "PRODUCT_CACHE", key = "'allProducts'")
    )
    public ProductDTO update(Long id, ProductDTO productDTO) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());

        return productMapper.toDto(productRepository.save(product));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "PRODUCT_CACHE", key = "#id"),
                    @CacheEvict(value = "PRODUCT_CACHE", key = "'allProducts'")
            }
    )
    public void deleteById(Long id) {

        productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        productRepository.deleteById(id);
    }

    @CacheEvict(value = "PRODUCT_CACHE", key = "'allProducts'")
    public void deleteAll() {

        if(productRepository.findAll().isEmpty()) {

            throw new ProductNotFoundException("No products were found");
        }

        productRepository.deleteAll();
    }
}
