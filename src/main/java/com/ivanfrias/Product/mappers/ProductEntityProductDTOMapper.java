package com.ivanfrias.Product.mappers;

import com.ivanfrias.Product.model.ProductEntity;
import com.ivanfrias.products.model.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductEntityProductDTOMapper {

    @Mapping(target = "createdAt", expression = "java(mapOffsetToZonedDateTime(productDTO.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(mapOffsetToZonedDateTime(productDTO.getUpdatedAt()))")
    ProductEntity productDTOToProductEntity(ProductDTO productDTO);

    @Mapping(target = "createdAt", expression = "java(mapZonedDateTimeToOffset(productEntity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(mapZonedDateTimeToOffset(productEntity.getUpdatedAt()))")
    @Mapping(target = "categoryId", source = "productEntity.category.id")
    ProductDTO productEntityToProductDTO(ProductEntity productEntity);

    List<ProductEntity> productDTOListToProductEntityList(List<ProductDTO> productDTOS);

    List<ProductDTO> productEntityListToProductDTOList(List<ProductEntity> productEntities);

    default ZonedDateTime mapOffsetToZonedDateTime(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null ? offsetDateTime.toZonedDateTime() : null;
    }

    default OffsetDateTime mapZonedDateTimeToOffset(ZonedDateTime zonedDateTime) {
        return zonedDateTime != null ? zonedDateTime.toOffsetDateTime() : null;
    }
}
