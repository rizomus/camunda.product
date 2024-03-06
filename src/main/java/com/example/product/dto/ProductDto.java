package com.example.product.dto;

import com.example.product.entity.CurrencyUnit;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductDto (
        long article,
        String description,
        BigDecimal cost,
        CurrencyUnit currencyUnit,
        int amount
){
}
