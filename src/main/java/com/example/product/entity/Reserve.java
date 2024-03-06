package com.example.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reserve {

    @Id
    @GeneratedValue
    int id;

    private long orderId;

    private int amount;

    public Reserve(long orderId, int amount) {
        this.orderId = orderId;
        this.amount = amount;
    }
}
