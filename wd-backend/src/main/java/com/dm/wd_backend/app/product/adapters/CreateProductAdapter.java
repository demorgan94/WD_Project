package com.dm.wd_backend.app.product.adapters;

import com.dm.wd_backend.app.product.usecases.CreateProduct;
import com.dm.wd_backend.domain.product.gateway.ProductGateway;
import com.dm.wd_backend.domain.product.model.Product;

public class CreateProductAdapter implements CreateProduct {
    private final ProductGateway productGateway;

    public CreateProductAdapter(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public Product execute(Product product) {
        return productGateway.create(product);
    }
}
