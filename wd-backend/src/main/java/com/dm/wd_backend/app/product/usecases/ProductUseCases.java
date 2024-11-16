package com.dm.wd_backend.app.product.usecases;

import com.dm.wd_backend.domain.product.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductUseCases {
    Product findProductById(UUID id);
    List<Product> findAllProducts();
    Product saveProduct(Product product);
    boolean deleteProduct(UUID id);
}
