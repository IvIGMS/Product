package com.ivanfrias.Product.api;

import com.ivanfrias.Product.api.utils.PaginationUtils;
import com.ivanfrias.Product.services.ProductService;
import com.ivanfrias.products.api.ProductsApi;

import com.ivanfrias.products.model.PagedResponse;
import com.ivanfrias.products.model.PagedResponseProductDTO;
import com.ivanfrias.products.model.ProductDTO;
import com.ivanfrias.products.model.ProductRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class ProductController implements ProductsApi {

    private final ProductService productService;

    @Override
    public ResponseEntity<ProductDTO> createProduct(ProductRequestDTO productRequestDTO) {
        return ResponseEntity.created(null).body(productService.createProduct(productRequestDTO));
    }

    @Override
    public ResponseEntity<Void> deleteProductById(Long productId) {
        productService.deleteById(productId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ProductDTO> getProductById(Long productId) {
        return ResponseEntity.ok(productService.getById(productId));
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getProductsFilter(String productName, String categoryName, Double minPrice, Double maxPrice, Long storeId) {
        return ResponseEntity.ok(productService.getProductsFilter(productName, categoryName, minPrice, maxPrice, storeId));
    }

    @Override
    public ResponseEntity<ProductDTO> updateProductById(Long productId, ProductRequestDTO productRequestDTO) {
        return ResponseEntity.ok(productService.updateProductById(productId, productRequestDTO));
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getProductByStoreId(Long storeId) {
        return ResponseEntity.ok(productService.getProductByStoreId(storeId));
    }

    @Override
    public ResponseEntity<PagedResponseProductDTO> getPagedProductsFilter(
            String productName,
            String categoryName,
            Double minPrice,
            Double maxPrice,
            Long storeId,
            Integer pageNumberQueryParam,
            Integer pageSizeQueryParam,
            String sortByQueryParam
    ) {
        Pageable pageable = PaginationUtils.createPageable(pageNumberQueryParam, pageSizeQueryParam, sortByQueryParam);

        Page<ProductDTO> productDTOlist = productService.getPagedProductsFilter(productName, categoryName, minPrice, maxPrice, storeId, pageable);

        return ResponseEntity.ok(PaginationUtils.fromPage(productDTOlist));
    }
}
