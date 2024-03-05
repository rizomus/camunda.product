package com.example.product.dto;

import com.example.product.entity.CurrencyUnit;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
public class ProductInfoDto {

    long id;
    long article;
    String description;
    BigDecimal cost;
    CurrencyUnit currency_unit;
    int amount;
}
