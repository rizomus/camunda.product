package com.example.product.dto;

import com.example.product.entity.CurrencyUnit;
import com.example.product.entity.Product;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

public record StorefrontDto (

        Product product,

        BigDecimal cost,

        CurrencyUnit currencyUnit,

        int amount
){
}
