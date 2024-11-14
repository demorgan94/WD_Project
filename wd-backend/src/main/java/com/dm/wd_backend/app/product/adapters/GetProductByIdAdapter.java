package com.dm.wd_backend.app.product.adapters;

import com.dm.wd_backend.app.product.usecases.GetProductById;
import com.dm.wd_backend.domain.product.exceptions.ProductNotFoundException;
import com.dm.wd_backend.domain.product.gateway.ProductGateway;
import com.dm.wd_backend.domain.product.model.Product;

import java.util.UUID;

public class GetProductByIdAdapter implements GetProductById {
    private final ProductGateway productGateway;

    public GetProductByIdAdapter(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public Product execute(UUID id) {
        return productGateway.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }
}
