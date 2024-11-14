package com.dm.wd_backend.app.product.adapters;

import com.dm.wd_backend.app.product.usecases.GetAllProducts;
import com.dm.wd_backend.domain.product.gateway.ProductGateway;
import com.dm.wd_backend.domain.product.model.Product;

import java.util.List;

public class GetAllProductsAdapter implements GetAllProducts {
    private final ProductGateway productGateway;

    public GetAllProductsAdapter(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public List<Product> execute() {
        return productGateway.findAll();
    }
}
