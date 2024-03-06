package com.example.product.exception;

public class NotEnoughAmountForReserve extends RuntimeException {
    public NotEnoughAmountForReserve(String message) {
        super(message);
    }
}
