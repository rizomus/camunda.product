package com.example.product.entity;

import com.example.product.dto.StorefrontDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Check(constraints = "amount >= 0")
@Check(constraints = "cost > 0")
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

    @Column

    private int amount;

    public Storefront(StorefrontDto dto) {

        this.product = dto.product();
        this.currencyUnit = dto.currencyUnit();
        this.amount = dto.amount();
        this.cost = dto.cost();

    }

}


