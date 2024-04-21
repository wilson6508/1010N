package com.exception;

public class MyException extends RuntimeException {

    public MyException(String message) {
        super(message);
        System.out.println(message);
    }

}
