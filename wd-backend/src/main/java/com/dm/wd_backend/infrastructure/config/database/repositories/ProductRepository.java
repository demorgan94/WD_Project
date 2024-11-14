package com.dm.wd_backend.infrastructure.config.database.repositories;

import com.dm.wd_backend.infrastructure.config.database.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
}
