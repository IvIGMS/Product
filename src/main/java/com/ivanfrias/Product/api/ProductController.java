package com.ivanfrias.Product.api;

import com.ivanfrias.Product.services.ProductService;
import com.ivanfrias.products.api.ProductsApi;

import com.ivanfrias.products.model.ProductDTO;
import com.ivanfrias.products.model.ProductRequestDTO;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<ProductDTO>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @Override
    public ResponseEntity<ProductDTO> updateProductById(Long productId, ProductRequestDTO productRequestDTO) {
        return ResponseEntity.ok(productService.updateProductById(productId, productRequestDTO));
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getProductByStoreId(Long storeId) {
        return ResponseEntity.ok(productService.getProductByStoreId(storeId));
    }


}
