package com.supermarketapi.exception;

public class SupermarketAlreadyExists extends RuntimeException{
    public SupermarketAlreadyExists(String message) {
        super(message);
    }
}
