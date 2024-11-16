package com.dm.wd_backend.infrastructure.product.gateway;

import com.dm.wd_backend.domain.product.model.Product;
import com.dm.wd_backend.infrastructure.config.database.entities.ProductEntity;
import com.dm.wd_backend.infrastructure.config.database.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ProductDBGatewayTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductDBGateway productDBGateway;

    private Product existingProduct;
    private Product newProduct;

    @BeforeEach
    void setUp() {
        newProduct = new Product(null, "name", "description", 10.99, 10);
        existingProduct = new Product(UUID.randomUUID(), "name", "description", 20.99, 20);
    }

    @Test
    void givenNewProduct_whenSave_thenProductIsSavedWithGeneratedId() {
        // Given
        doReturn(ProductEntity.builder()
                .id(UUID.randomUUID())
                .name(newProduct.getName())
                .description(newProduct.getDescription())
                .price(newProduct.getPrice())
                .quantity(newProduct.getQuantity())
                .build()).when(productRepository).save(any(ProductEntity.class));

        // When
        Product savedProduct = productDBGateway.save(newProduct);

        // Then
        assertNotNull(savedProduct.getId());
        assertEquals(newProduct.getName(), savedProduct.getName());
        assertEquals(newProduct.getDescription(), savedProduct.getDescription());
        assertEquals(newProduct.getPrice(), savedProduct.getPrice());
        assertEquals(newProduct.getQuantity(), savedProduct.getQuantity());
    }

    @Test
    void givenExistingProduct_whenSave_thenProductIsUpdated() {
        // Given
        ProductEntity existingProductEntity = ProductEntity.fromDomain(existingProduct);
        doReturn(Optional.of(existingProductEntity)).when(productRepository).findById(existingProduct.getId());

        // When
        existingProductEntity.setName("new name");
        ProductEntity updatedProductEntity = ProductEntity.builder()
                .id(existingProductEntity.getId())
                .name(existingProductEntity.getName())
                .description(existingProductEntity.getDescription())
                .price(existingProductEntity.getPrice())
                .quantity(existingProductEntity.getQuantity())
                .build();
        doReturn(updatedProductEntity).when(productRepository).save(any(ProductEntity.class));

        existingProduct.setName("new name"); // Update the existingProduct object
        Product updatedProduct = productDBGateway.save(existingProduct);

        // Then
        assertEquals(existingProduct.getId(), updatedProduct.getId());
        assertEquals(existingProduct.getName(), updatedProduct.getName());
        assertEquals(existingProduct.getDescription(), updatedProduct.getDescription());
        assertEquals(existingProduct.getPrice(), updatedProduct.getPrice());
        assertEquals(existingProduct.getQuantity(), updatedProduct.getQuantity());
    }


    @Test
    void givenProduct_whenDelete_thenProductIsDeleted() {
        // Given
        ProductEntity existingProductEntity = ProductEntity.fromDomain(existingProduct);
        doReturn(Optional.of(existingProductEntity)).when(productRepository).findById(existingProduct.getId());

        // When
        boolean result = productDBGateway.delete(existingProduct.getId());

        // Then
        assertTrue(result);
    }

    @Test
    void givenProduct_whenFindById_thenProductIsReturned() {
        // Given
        ProductEntity productEntity = ProductEntity.fromDomain(existingProduct);
        doReturn(Optional.of(productEntity)).when(productRepository).findById(existingProduct.getId());

        // When
        Optional<Product> foundProduct = productDBGateway.findById(existingProduct.getId());

        // Then
        assertTrue(foundProduct.isPresent());
        assertEquals(existingProduct, foundProduct.get());
    }

    @Test
    void givenProducts_whenFindAll_thenAllProductsAreReturned() {
        // Given
        ProductEntity productEntity1 = ProductEntity.fromDomain(existingProduct);
        ProductEntity productEntity2 = ProductEntity.fromDomain(new Product(UUID.randomUUID(), "name2", "description2", 20.99, 20));
        doReturn(List.of(productEntity1, productEntity2)).when(productRepository).findAll();

        // When
        List<Product> allProducts = productDBGateway.findAll();

        // Then
        assertEquals(2, allProducts.size());
        assertTrue(allProducts.contains(existingProduct));
    }
}
