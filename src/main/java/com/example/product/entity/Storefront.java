package com.example.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Storefront {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    private BigDecimal cost;

    @Enumerated(EnumType.STRING)
    private CurrencyUnit currencyUnit;

    private int amount;

}


