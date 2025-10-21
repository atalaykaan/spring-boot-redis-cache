package com.atalaykaan.spring_boot_redis_cache.controller;

import com.atalaykaan.spring_boot_redis_cache.dto.ProductDTO;
import com.atalaykaan.spring_boot_redis_cache.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findProductWithId(@PathVariable Long id) {

        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAllProducts() {

        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(productService.save(productDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {

        return ResponseEntity.ok(productService.update(id, productDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {

        productService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllProducts() {

        productService.deleteAll();

        return ResponseEntity.noContent().build();
    }
}
