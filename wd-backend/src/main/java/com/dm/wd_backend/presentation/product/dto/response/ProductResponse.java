package com.dm.wd_backend.presentation.product.dto.response;

import com.dm.wd_backend.domain.product.model.Product;

import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String description,
        double price,
        int quantity
) {
    public static ProductResponse of(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity()
        );
    }
}
