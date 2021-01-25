package com.fitness.demo.Exceptions;

public class NoAuthenticationFoundException extends RuntimeException {
    public NoAuthenticationFoundException(){
        super("No authentication found!");
    }
}
