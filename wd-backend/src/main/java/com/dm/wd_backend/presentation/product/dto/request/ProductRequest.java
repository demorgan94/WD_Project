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
    /**
     * Creates a domain object from the request. If the id is null, a new product will be created.
     * @return a new Product instance
     */
    public Product toDomain() {
        return toDomain(null);
    }

    /**
     * Creates a domain object from the request.
     * <p>
     * If the id is null, a new product will be created. Otherwise, the product with the given id will be updated.
     * @param id the id of the product to be created or updated
     * @return a new or updated Product instance
     */
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
