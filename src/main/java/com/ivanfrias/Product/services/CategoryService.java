package com.ivanfrias.Product.services;

import com.ivanfrias.Product.exceptions.DataBaseErrorException;
import com.ivanfrias.Product.exceptions.NotFoundException;
import com.ivanfrias.Product.mappers.CategoryEntityCategoryDTOMapper;
import com.ivanfrias.Product.mappers.CategoryEntityCategoryRequestDTOMapper;
import com.ivanfrias.Product.model.CategoryEntity;
import com.ivanfrias.Product.model.ProductEntity;
import com.ivanfrias.Product.repositories.CategoryRepository;
import com.ivanfrias.products.model.CategoryDTO;
import com.ivanfrias.products.model.CategoryRequestDTO;
import com.ivanfrias.products.model.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryEntityCategoryDTOMapper categoryEntityCategoryDTOMapper;
    private final CategoryEntityCategoryRequestDTOMapper categoryEntityCategoryRequestDTOMapper;

    public List<CategoryDTO> getCategories() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();

        if(CollectionUtils.isEmpty(categoryEntities)){
            throw new NotFoundException("No hay ninguna categoria registrada en la aplicación");
        }
        return categoryEntityCategoryDTOMapper.categoryEntityListTocategoryDTOList(categoryEntities);
    }

    public CategoryDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        try {
            CategoryEntity categoryEntityToBeSaved = categoryEntityCategoryRequestDTOMapper.categoryRequestDTOToCategoryEntity(categoryRequestDTO);
            CategoryEntity categoryEntitySaved = categoryRepository.save(categoryEntityToBeSaved);
            return categoryEntityCategoryDTOMapper.categoryEntityToCategoryDTO(categoryEntitySaved);
        } catch (Exception e) {
            throw new DataBaseErrorException("Error al introducir la categoría en la base de datos");
        }
    }

    public CategoryDTO getCategoryById(Long categoryId) {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(categoryId);
        if (categoryEntityOptional.isPresent()){
            return categoryEntityCategoryDTOMapper.categoryEntityToCategoryDTO(categoryEntityOptional.get());
        } else {
            throw new NotFoundException("No existe una categoría con el id indicado");
        }
    }

    public CategoryEntity getCategoryEntityById(Long categoryId) {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(categoryId);
        if (categoryEntityOptional.isPresent()){
            return categoryEntityOptional.get();
        } else {
            throw new NotFoundException("No existe una categoría con el id indicado");
        }
    }

    public void deleteById(Long categoryId) {
        getCategoryById(categoryId);
        try {
            categoryRepository.deleteById(categoryId);
        } catch (Exception e){
            throw new DataBaseErrorException("Error al eliminar la categoría de la base de datos. Asegúrate de que está vacía.");
        }
    }

    public CategoryDTO updateProductById(Long categoryId, CategoryRequestDTO categoryRequestDTO) {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(categoryId);
        CategoryEntity categoryEntity = new CategoryEntity();
        if (categoryEntityOptional.isPresent()){
            categoryEntity = categoryEntityOptional.get();
            categoryEntity.setCategoryName(categoryRequestDTO.getCategoryName());
            categoryEntity.setStoreId(categoryRequestDTO.getStoreId());
            categoryRepository.save(categoryEntity);
        } else {
            throw new NotFoundException("No existe una categoria con el id indicado");
        }
        return categoryEntityCategoryDTOMapper.categoryEntityToCategoryDTO(categoryEntity);
    }
}
