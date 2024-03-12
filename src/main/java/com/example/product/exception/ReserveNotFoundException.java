package com.example.product.exception;

public class ReserveNotFoundException extends RuntimeException {
    public ReserveNotFoundException(String s) {
        super(s);
    }
}
