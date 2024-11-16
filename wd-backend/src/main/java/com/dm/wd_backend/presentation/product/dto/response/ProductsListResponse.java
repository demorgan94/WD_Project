package com.dm.wd_backend.presentation.product.dto.response;

import com.dm.wd_backend.domain.product.model.Product;

import java.util.List;

public record ProductsListResponse(
        List<ProductResponse> products
) {
    /**
     * Converts a list of products into a response object.
     *
     * @param products List of products
     * @return Response object containing a list of products
     */
    public static ProductsListResponse of(List<Product> products) {
        return new ProductsListResponse(products.stream()
                .map(ProductResponse::of)
                .toList());
    }
}
