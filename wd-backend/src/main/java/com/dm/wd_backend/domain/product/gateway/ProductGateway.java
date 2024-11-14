package com.dm.wd_backend.domain.product.gateway;

import com.dm.wd_backend.domain.product.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductGateway {
    Product create(Product product);
    Product update(Product product);
    void delete(Long id);

    Optional<Product> findById(Long id);
    List<Product> findAll();
}
