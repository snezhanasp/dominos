package com.example.dominos.model.exceptions;

public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException(String msg){
        super(msg);
    }
}
