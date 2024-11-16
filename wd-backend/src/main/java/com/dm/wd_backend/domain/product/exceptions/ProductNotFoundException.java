package com.dm.wd_backend.domain.product.exceptions;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotFoundException(UUID id) {
        super("Product not found with id: " + id);
    }
}
