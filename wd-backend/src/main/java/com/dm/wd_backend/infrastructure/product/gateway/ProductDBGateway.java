package com.dm.wd_backend.infrastructure.product.gateway;

import com.dm.wd_backend.domain.product.exceptions.ProductNotFoundException;
import com.dm.wd_backend.domain.product.gateway.ProductGateway;
import com.dm.wd_backend.domain.product.model.Product;
import com.dm.wd_backend.infrastructure.config.database.entities.ProductEntity;
import com.dm.wd_backend.infrastructure.config.database.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductDBGateway implements ProductGateway {
    private final ProductRepository productRepository;

    /**
     * Saves the given product to the database.
     *
     * @param product the product to be saved
     * @return the saved product with any database-generated values populated
     */
    @Override
    public Product save(Product product) {
        if (product.getId() != null) {
            return this.findById(product.getId())
                    .orElseThrow(() -> new ProductNotFoundException(product.getId()))
                    .updateProduct(product);
        }
        return this.productRepository.save(ProductEntity.fromDomain(product)).toDomain();
    }

    /**
     * Deletes the product with the given id from the database.
     *
     * @param id the id of the product to be deleted
     * @return true if the product was successfully deleted, false if no product was found with the given id
     */
    @Override
    public boolean delete(UUID id) {
        productRepository.deleteById(id);
        return this.findById(id).isEmpty();
    }

    /**
     * Retrieves the product with the given id from the database.
     *
     * @param id the id of the product to be retrieved
     * @return an Optional containing the product with the given id, or an empty Optional if no product was found with that id
     */
    @Override
    public Optional<Product> findById(UUID id) {
        return this.productRepository.findById(id)
                .map(ProductEntity::toDomain);
    }

    /**
     * Retrieves all products from the database.
     *
     * @return a list of all products in the database
     */
    @Override
    public List<Product> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductEntity::toDomain).toList();
    }
}
