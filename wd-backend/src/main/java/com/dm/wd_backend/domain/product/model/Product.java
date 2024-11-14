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

    private boolean isValidPrice(double price) {
        return price > 0;
    }
}
