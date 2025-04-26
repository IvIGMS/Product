package com.ivanfrias.Product.api;

import com.ivanfrias.Product.services.CategoryService;
import com.ivanfrias.products.api.CategoriesApi;
import com.ivanfrias.products.model.CategoryDTO;
import com.ivanfrias.products.model.CategoryRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class CategoryController implements CategoriesApi {
    private final CategoryService categoryService;

    @Override
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @Override
    public ResponseEntity<CategoryDTO> createCategory(CategoryRequestDTO categoryRequestDTO) {
        return ResponseEntity.created(null).body(categoryService.createCategory(categoryRequestDTO));
    }

    @Override
    public ResponseEntity<CategoryDTO> getCategoryById(Long categoryId) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    @Override
    public ResponseEntity<CategoryDTO> updateCategoryById(Long categoryId, CategoryRequestDTO categoryRequestDTO) {
        return ResponseEntity.ok(categoryService.updateProductById(categoryId, categoryRequestDTO));

    }

    @Override
    public ResponseEntity<Void> deleteCategoryById(Long categoryId) {
        categoryService.deleteById(categoryId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<CategoryDTO>> getCategoriesByStoreId(Long storeId) {
        return ResponseEntity.ok(categoryService.getCategoriesByStoreId(storeId));
    }
}
