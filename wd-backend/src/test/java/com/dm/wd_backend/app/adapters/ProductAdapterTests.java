package com.dm.wd_backend.app.adapters;

import com.dm.wd_backend.app.product.adapters.ProductAdapter;
import com.dm.wd_backend.domain.product.exceptions.ProductNotFoundException;
import com.dm.wd_backend.domain.product.exceptions.ProductsNotFoundException;
import com.dm.wd_backend.domain.product.gateway.ProductGateway;
import com.dm.wd_backend.domain.product.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ProductAdapterTests {

    @Mock
    private ProductGateway productGateway;

    @InjectMocks
    private ProductAdapter productAdapter;

    @Test
    void givenExistingProduct_whenFindProductById_thenReturnProduct() {
        // Given
        UUID id = UUID.randomUUID();
        Product product = new Product(id, "name", "description", 10.99, 10);
        doReturn(Optional.of(product)).when(productGateway).findById(id);

        // When
        Product result = productAdapter.findProductById(id);

        // Then
        assertEquals(product, result);
    }

    @Test
    void givenNonExistingProduct_whenFindProductById_thenThrowProductNotFoundException() {
        // Given
        UUID id = UUID.randomUUID();
        doReturn(Optional.empty()).when(productGateway).findById(id);

        // When and Then
        assertThrows(ProductNotFoundException.class, () -> productAdapter.findProductById(id));
    }

    @Test
    void givenProductsInDatabase_whenFindAllProducts_thenReturnProducts() {
        // Given
        List<Product> products = List.of(
                new Product(UUID.randomUUID(), "name1", "description1", 10.99, 10),
                new Product(UUID.randomUUID(), "name2", "description2", 20.99, 20)
        );
        doReturn(products).when(productGateway).findAll();

        // When
        List<Product> result = productAdapter.findAllProducts();

        // Then
        assertEquals(products, result);
    }

    @Test
    void givenNoProductsInDatabase_whenFindAllProducts_thenThrowProductsNotFoundException() {
        // Given
        doReturn(null).when(productGateway).findAll();

        // When and Then
        assertThrows(ProductsNotFoundException.class, () -> productAdapter.findAllProducts());
    }

    @Test
    void givenValidProduct_whenSaveProduct_thenReturnSavedProduct() {
        // Given
        Product product = new Product(UUID.randomUUID(), "name", "description", 10.99, 10);
        doReturn(product).when(productGateway).save(any());

        // When
        Product result = productAdapter.saveProduct(product);

        // Then
        assertEquals(product, result);
    }

    @Test
    void givenInvalidProduct_whenSaveProduct_thenThrowProductNotFoundException() {
        // Given
        Product product = new Product(UUID.randomUUID(), "name", "description", 10.99, 10);
        doReturn(null).when(productGateway).save(any());

        // When and Then
        assertThrows(ProductNotFoundException.class, () -> productAdapter.saveProduct(product));
    }

    @Test
    void givenExistingProduct_whenDeleteProduct_thenReturnTrue() {
        // Given
        UUID id = UUID.randomUUID();
        doReturn(true).when(productGateway).delete(id);

        // When
        boolean result = productAdapter.deleteProduct(id);

        // Then
        assertTrue(result);
    }

    @Test
    void givenNonExistingProduct_whenDeleteProduct_thenReturnFalse() {
        // Given
        UUID id = UUID.randomUUID();
        doReturn(false).when(productGateway).delete(id);

        // When
        boolean result = productAdapter.deleteProduct(id);

        // Then
        assertFalse(result);
    }
}
