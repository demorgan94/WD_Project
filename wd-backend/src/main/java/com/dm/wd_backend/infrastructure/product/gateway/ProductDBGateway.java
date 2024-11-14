package com.dm.wd_backend.infrastructure.product.gateway;

import com.dm.wd_backend.domain.product.gateway.ProductGateway;
import com.dm.wd_backend.domain.product.model.Product;
import com.dm.wd_backend.infrastructure.config.database.entities.ProductEntity;
import com.dm.wd_backend.infrastructure.config.database.repositories.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductDBGateway implements ProductGateway {
    private final ProductRepository productRepository;

    public ProductDBGateway(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {
        return this.productRepository.save(ProductEntity.fromDomain(product)).toDomain();
    }

    @Override
    public Product update(Product product) {
        return this.productRepository.save(ProductEntity.fromDomain(product)).toDomain();
    }

    @Override
    public void delete(UUID id) {
        productRepository.deleteById(id);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return productRepository.findById(id)
                .map(ProductEntity::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductEntity::toDomain)
                .toList();
    }
}
