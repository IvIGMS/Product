package com.ivanfrias.Product.exceptions;

public class DataBaseErrorException extends RuntimeException{
    public DataBaseErrorException(String message) {
        super(message);
    }

    public DataBaseErrorException(String message, Object... args) {
        super(String.format(message, args));
    }
}
