package com.example.product.exception;

public class ArticlesNotFoundException extends RuntimeException {

    public ArticlesNotFoundException() {
    }
    public ArticlesNotFoundException(String message) {
        super(message);
    }
}
