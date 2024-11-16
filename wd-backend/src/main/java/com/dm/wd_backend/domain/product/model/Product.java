package com.dm.wd_backend.domain.product.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Product {
    private UUID id;
    private String name;
    private String description;
    private double price;
    private int quantity;

    public Product(UUID id, String name, String description, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Updates the current product with new values from the given product.
     *
     * @param product the product to copy values from
     * @return a new product with the updated values
     */
    public Product updateProduct(Product product) {
        return Product.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }
}
