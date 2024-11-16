package com.dm.wd_backend.app.product.adapters;

import com.dm.wd_backend.app.product.usecases.ProductUseCases;
import com.dm.wd_backend.domain.product.exceptions.ProductNotFoundException;
import com.dm.wd_backend.domain.product.exceptions.ProductsNotFoundException;
import com.dm.wd_backend.domain.product.gateway.ProductGateway;
import com.dm.wd_backend.domain.product.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductAdapter implements ProductUseCases {
    private final ProductGateway productGateway;

    /**
     * Find a product by id.
     *
     * @param id the id of the product
     * @return the product with the given id
     * @throws ProductNotFoundException if no product with the given id was found
     */
    @Override
    public Product findProductById(UUID id) {
        log.debug("Finding product with id: {}", id);
        return productGateway.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }


    /**
     * Find all products.
     *
     * @return a list of all products
     * @throws ProductsNotFoundException if no products were found
     */
    @Override
    public List<Product> findAllProducts() {
        log.debug("Finding all products");
        return Optional.ofNullable(productGateway.findAll())
                .orElseThrow(ProductsNotFoundException::new);
    }


    /**
     * Saves a product.
     *
     * @param product the product to be saved
     * @return the saved product with any database-generated values populated
     * @throws ProductNotFoundException if no product with the given id was found after saving
     */
    @Override
    public Product saveProduct(Product product) {
        log.debug("Saving product: {}", product);
        return Optional.ofNullable(productGateway.save(product))
                .orElseThrow(() -> new ProductNotFoundException(product.getId()));
    }


    /**
     * Deletes a product by its id.
     *
     * @param id the id of the product to be deleted
     * @return true if the product was successfully deleted, false if no product was found with the given id
     */
    @Override
    public boolean deleteProduct(UUID id) {
        log.debug("Deleting product with id: {}", id);
        return productGateway.delete(id);
    }
}
