package com.example.product.entity;

import com.example.product.dto.ProductDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private Long article;

    private String description;

    public Product(Long article, String description) {
        this.article = article;
        this.description = description;
    }

    public Product(ProductDto dto) {
        this.article = dto.article();
        this.description = dto.description();
    }

}
