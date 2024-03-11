package com.example.product.dto;

public record OrderDto (

        long orderId,
        Long article,

        String description,
        int amount
){
}
