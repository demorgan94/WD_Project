package com.dm.wd_backend.app.product.usecases;

import com.dm.wd_backend.domain.product.model.Product;

public interface UpdateProduct {
    Product execute(Product product);
}
