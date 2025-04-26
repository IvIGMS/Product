package com.ivanfrias.Product.mappers;

import com.ivanfrias.Product.model.CategoryEntity;
import com.ivanfrias.products.model.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryEntityCategoryDTOMapper {
    @Mapping(target = "createdAt", expression = "java(mapZonedDateTimeToOffset(categoryEntity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(mapZonedDateTimeToOffset(categoryEntity.getUpdatedAt()))")
    CategoryDTO categoryEntityToCategoryDTO(CategoryEntity categoryEntity);

    @Mapping(target = "createdAt", expression = "java(mapOffsetToZonedDateTime(categoryDTO.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(mapOffsetToZonedDateTime(categoryDTO.getUpdatedAt()))")
    CategoryEntity categoryDTOToCategoryEntity(CategoryDTO categoryDTO);

    List<CategoryDTO> categoryEntityListTocategoryDTOList(List<CategoryEntity> categoryEntities);
    List<CategoryEntity> categoryDTOListToCategoryEntityList(List<CategoryDTO> categoryDTOS);

    default ZonedDateTime mapOffsetToZonedDateTime(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null ? offsetDateTime.toZonedDateTime() : null;
    }

    default OffsetDateTime mapZonedDateTimeToOffset(ZonedDateTime zonedDateTime) {
        return zonedDateTime != null ? zonedDateTime.toOffsetDateTime() : null;
    }
}
