package com.dm.wd_backend.app.product.usecases;

import java.util.UUID;

public interface DeleteProduct {
    void execute(UUID id);
}
