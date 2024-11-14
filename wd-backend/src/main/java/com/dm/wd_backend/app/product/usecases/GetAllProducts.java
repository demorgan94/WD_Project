package com.dm.wd_backend.app.product.usecases;

import com.dm.wd_backend.domain.product.model.Product;

import java.util.List;

public interface GetAllProducts {
    List<Product> execute();
}
