package com.example.springboottesttask.exception;

public class LordNotFoundException  extends RuntimeException{
    public LordNotFoundException(String message) {
        super(message);
    }
}
