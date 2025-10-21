package com.atalaykaan.spring_boot_redis_cache.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Transient
    public static final String SEQUENCE_NAME = "products_sequence";

    @Id
    private Long id;

    private String name;

    private BigDecimal price;
}
