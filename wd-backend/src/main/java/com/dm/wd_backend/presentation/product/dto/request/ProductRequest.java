package com.dm.wd_backend.presentation.product.dto.request;

import com.dm.wd_backend.domain.product.model.Product;
import com.dm.wd_backend.presentation.product.dto.validations.Price;
import com.dm.wd_backend.presentation.product.dto.validations.Quantity;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ProductRequest(
        @NotBlank String name,
        @NotBlank String description,
        @Price double price,
        @Quantity int quantity
) {
    public Product toDomain() {
        return toDomain(null);
    }

    public Product toDomain(UUID id) {
        return Product.builder()
                .id(id)
                .name(name)
                .description(description)
                .price(price)
                .quantity(quantity)
                .build();
    }
}
