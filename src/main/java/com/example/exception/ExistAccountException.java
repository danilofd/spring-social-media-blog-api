package com.example.exception;

public class ExistAccountException extends RuntimeException {

    public ExistAccountException(String msg){
        super(msg);
    }
}