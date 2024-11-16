package com.dm.wd_backend.infrastructure.config.database.repositories;

import com.dm.wd_backend.infrastructure.config.database.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
}
