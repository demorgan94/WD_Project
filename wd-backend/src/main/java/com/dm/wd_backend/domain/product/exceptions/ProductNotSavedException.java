package com.dm.wd_backend.domain.product.exceptions;

public class ProductNotSavedException extends RuntimeException {
    public ProductNotSavedException(String message) {
        super(message);
    }

    public ProductNotSavedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotSavedException() {
        super("Product not saved");
    }
}
