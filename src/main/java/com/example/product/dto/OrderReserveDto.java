package com.example.product.dto;

import com.example.product.entity.CurrencyUnit;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderReserveDto(
        long orderId,
        BigDecimal paymentSum,
        CurrencyUnit currencyUnit
) {
}
