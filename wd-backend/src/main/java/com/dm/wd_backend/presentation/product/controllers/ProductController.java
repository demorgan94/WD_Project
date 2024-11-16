package com.dm.wd_backend.presentation.product.controllers;

import com.dm.wd_backend.app.product.usecases.ProductUseCases;
import com.dm.wd_backend.presentation.product.dto.request.ProductRequest;
import com.dm.wd_backend.presentation.product.dto.response.ProductResponse;
import com.dm.wd_backend.presentation.product.dto.response.ProductsListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@Tag(name = "Products", description = "The products API")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductUseCases productUseCases;

    /**
     * Retrieves a product by id.
     *
     * @param id the id of the product
     * @return a response with the product if found, otherwise a 404 response
     */
    @Operation(summary = "Get a product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = { @Content(mediaType = "application/json", schema = @Schema()) })
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID id) {
        Optional<ProductResponse> productResponse =
                Optional.of(ProductResponse.of(productUseCases.findProductById(id)));
        return productResponse.map(product -> ResponseEntity.ok().body(product))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all products.
     *
     * @return a response with all products
     */
    @Operation(summary = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductsListResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json", schema = @Schema()) })
    })
    @GetMapping
    public ResponseEntity<ProductsListResponse> getAllProducts() {
        Optional<ProductsListResponse> productsListResponse =
                Optional.of(ProductsListResponse.of(productUseCases.findAllProducts()));
        return productsListResponse.map(products -> ResponseEntity.ok().body(products))
                .orElse(ResponseEntity.internalServerError().build());
    }

    /**
     * Creates a new product.
     *
     * @param productRequest the product request containing product details
     * @return a response entity containing the created product, or an internal server error response if creation fails
     */
    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json", schema = @Schema()) })
    })
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        Optional<ProductResponse> productResponse =
                Optional.of(ProductResponse.of(productUseCases.saveProduct(productRequest.toDomain())));
        return productResponse.map(product -> ResponseEntity.status(HttpStatus.CREATED).body(product))
                .orElse(ResponseEntity.internalServerError().build());
    }

    /**
     * Updates an existing product.
     *
     * @param id the UUID of the product to be updated
     * @param productRequest the request body containing updated product details
     * @return a ResponseEntity containing the updated product if successful, or an internal server error response if the update fails
     */
    @Operation(summary = "Update an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json", schema = @Schema()) })
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable UUID id, @RequestBody @Valid ProductRequest productRequest) {
        Optional<ProductResponse> productResponse =
                Optional.of(ProductResponse.of(productUseCases.saveProduct(productRequest.toDomain(id))));
        return productResponse.map(product -> ResponseEntity.ok().body(product))
                .orElse(ResponseEntity.internalServerError().build());
    }

    /**
     * Deletes a product by id.
     *
     * @param id the id of the product
     * @return 204 No Content if the product was successfully deleted, or 404 Not Found if no product with the given id was found
     */
    @Operation(summary = "Delete a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted",
                    content = { @Content(mediaType = "application/json", schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = { @Content(mediaType = "application/json", schema = @Schema()) })
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        return productUseCases.deleteProduct(id) ?
                ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
