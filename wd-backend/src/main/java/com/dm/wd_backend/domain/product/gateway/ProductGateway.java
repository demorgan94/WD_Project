package com.dm.wd_backend.domain.product.gateway;

import com.dm.wd_backend.domain.product.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductGateway {

    Product save(Product product);
    boolean delete(UUID id);
    Optional<Product> findById(UUID id);
    List<Product> findAll();
}
