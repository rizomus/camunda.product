package com.example.product.entity;

import com.example.product.dto.ProductDto;
import com.example.product.exception.NotEnoughAmountForReserve;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
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

    @Version
    private long version;

    private Long article;

    private String description;

    @Check(constraints = "cost > 0")
    private BigDecimal cost;


    @Enumerated(EnumType.STRING)
    private CurrencyUnit currencyUnit;


    @Check(constraints = "amount >= 0")
    private int amount;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private List<Reserve> reserve;


    public Product(ProductDto dto) {
        this.article = dto.article();
        this.description = dto.description();
        this.cost = dto.cost();
        this.currencyUnit = dto.currencyUnit();
        this.amount = dto.amount();
    }

    @Transient
    public ProductDto getDto () {
        return ProductDto.builder()
                .article(article)
                .description(description)
                .cost(cost)
                .currencyUnit(currencyUnit)
                .amount(amount)
                .build();
    }

    public void addReserve(long orderId, int amount) {
        if (this.amount >= amount) {
            this.reserve.add(new Reserve(orderId, amount));
            this.amount -= amount;
        } else {
            throw new NotEnoughAmountForReserve(new StringBuilder("")
                    .append("Неудачная попытка зарезервировать артикул ").append(this.article)
                    .append(" в количестве ").append(amount).append(".")
                    .append(" В наличии ").append(this.amount).append(". ").toString());
        }
    }
}
