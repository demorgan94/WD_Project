package com.dm.wd_backend.app.product.adapters;

import com.dm.wd_backend.app.product.usecases.UpdateProduct;
import com.dm.wd_backend.domain.product.gateway.ProductGateway;
import com.dm.wd_backend.domain.product.model.Product;

public class UpdateProductAdapter implements UpdateProduct {
    private final ProductGateway productGateway;

    public UpdateProductAdapter(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public Product execute(Product product) {
        return productGateway.update(product);
    }
}
