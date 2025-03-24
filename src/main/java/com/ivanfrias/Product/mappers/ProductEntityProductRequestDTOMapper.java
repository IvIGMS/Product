package com.ivanfrias.Product.mappers;

import com.ivanfrias.Product.model.ProductEntity;
import com.ivanfrias.products.model.ProductRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductEntityProductRequestDTOMapper {

    ProductEntity productRequestDTOToProductEntity(ProductRequestDTO productRequestDTO);

    ProductRequestDTO productEntityToProductRequestDTO(ProductEntity productEntity);
}
