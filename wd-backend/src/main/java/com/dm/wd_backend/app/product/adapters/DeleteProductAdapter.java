package com.dm.wd_backend.app.product.adapters;

import com.dm.wd_backend.app.product.usecases.DeleteProduct;
import com.dm.wd_backend.domain.product.gateway.ProductGateway;

import java.util.UUID;

public class DeleteProductAdapter implements DeleteProduct {
    private final ProductGateway productGateway;

    public DeleteProductAdapter(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public void execute(UUID id) {
        productGateway.delete(id);
    }
}
