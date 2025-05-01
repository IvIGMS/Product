package com.ivanfrias.Product.repositories;

import com.ivanfrias.Product.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query(value = "SELECT p FROM ProductEntity p" +
            " WHERE p.storeId = ?1")
    List<ProductEntity> getProductByStoreId(Long storeId);

    @Query("SELECT p FROM ProductEntity p " +
            "WHERE (:productName IS NULL OR p.productName = :productName) " +
            "AND (:categoryName IS NULL OR p.category.categoryName = :categoryName) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND (:storeId IS NULL OR p.storeId = :storeId)")
    List<ProductEntity> getProductsFilter(@Param("productName") String productName,
                                          @Param("categoryName") String categoryName,
                                          @Param("minPrice") Double minPrice,
                                          @Param("maxPrice") Double maxPrice,
                                          @Param("storeId") Long storeId);

    @Query("SELECT p FROM ProductEntity p " +
            "WHERE (:productName IS NULL OR p.productName = :productName) " +
            "AND (:categoryName IS NULL OR p.category.categoryName = :categoryName) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND (:storeId IS NULL OR p.storeId = :storeId)")
    Page<ProductEntity> getPagedProductsFilter(@Param("productName") String productName,
                                               @Param("categoryName") String categoryName,
                                               @Param("minPrice") Double minPrice,
                                               @Param("maxPrice") Double maxPrice,
                                               @Param("storeId") Long storeId,
                                               Pageable pageable);
}
