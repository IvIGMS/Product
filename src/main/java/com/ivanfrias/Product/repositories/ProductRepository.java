package com.ivanfrias.Product.repositories;

import com.ivanfrias.Product.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query(value = "SELECT p FROM ProductEntity p" +
            " WHERE p.storeId = ?1")
    List<ProductEntity> getProductByStoreId(Long storeId);
}
