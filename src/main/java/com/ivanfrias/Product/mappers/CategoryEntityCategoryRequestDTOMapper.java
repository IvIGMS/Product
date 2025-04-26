package com.ivanfrias.Product.mappers;

import com.ivanfrias.Product.model.CategoryEntity;
import com.ivanfrias.products.model.CategoryRequestDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CategoryEntityCategoryRequestDTOMapper {
    CategoryRequestDTO categoryEntityToCategoryRequestDTO(CategoryEntity categoryEntity);
    CategoryEntity categoryRequestDTOToCategoryEntity(CategoryRequestDTO categoryRequestDTO);
}
