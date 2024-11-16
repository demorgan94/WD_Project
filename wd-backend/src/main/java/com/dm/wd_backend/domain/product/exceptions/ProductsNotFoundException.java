package com.dm.wd_backend.domain.product.exceptions;

public class ProductsNotFoundException extends RuntimeException {
    public ProductsNotFoundException(String message) {
        super(message);
    }

    public ProductsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductsNotFoundException() {
        super("Products not found in database");
    }
}
