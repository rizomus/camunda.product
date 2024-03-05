package com.example.product.dto;

public record OrderDto (
        Long article,
        String description,
        int count
){
}
