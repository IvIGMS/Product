package com.ivanfrias.Product.repositories;

import com.ivanfrias.Product.model.CategoryEntity;
import com.ivanfrias.Product.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {


    @Query(value = "SELECT c FROM CategoryEntity c" +
            " WHERE c.storeId = ?1")
    List<CategoryEntity> getCategoriesByStoreId(Long storeId);
}
