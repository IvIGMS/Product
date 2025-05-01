package com.ivanfrias.Product.services;

import com.ivanfrias.Product.exceptions.DataBaseErrorException;
import com.ivanfrias.Product.exceptions.NotFoundException;
import com.ivanfrias.Product.mappers.ProductEntityProductDTOMapper;
import com.ivanfrias.Product.mappers.ProductEntityProductRequestDTOMapper;
import com.ivanfrias.Product.model.ProductEntity;
import com.ivanfrias.Product.repositories.ProductRepository;
import com.ivanfrias.products.model.ProductDTO;
import com.ivanfrias.products.model.ProductRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductEntityProductDTOMapper productEntityProductDTOMapper;
    private final ProductEntityProductRequestDTOMapper productEntityProductRequestDTOMapper;
    private final CategoryService categoryService;

    public ProductDTO createProduct(ProductRequestDTO productRequestDTO) {
        try{
            ProductEntity productEntityToBeSaved = productEntityProductRequestDTOMapper.productRequestDTOToProductEntity(productRequestDTO);
            productEntityToBeSaved.setCategory(categoryService.getCategoryEntityById(productRequestDTO.getCategoryId()));
            ProductEntity productEntitySaved = productRepository.save(productEntityToBeSaved);
            return productEntityProductDTOMapper.productEntityToProductDTO(productEntitySaved);
        } catch (NotFoundException e) {
            throw new NotFoundException("El id de la categoría no pertecene a ninguna categoría");
        } catch (Exception e){
            throw new DataBaseErrorException("Error al introducir el producto en la base de datos");
        }
    }

    public void deleteById(Long productId) {
        getById(productId);
        try {
            productRepository.deleteById(productId);
        } catch (Exception e){
            throw new DataBaseErrorException("Error al eliminar el producto de la base de datos");
        }
    }

    public ProductDTO getById(Long productId) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(productId);
        if (productEntityOptional.isPresent()){
            return productEntityProductDTOMapper.productEntityToProductDTO(productEntityOptional.get());
        } else {
            throw new NotFoundException("No existe un producto con el id indicado");
        }
    }

    public List<ProductDTO> getProductsFilter(String productName, String categoryName, Double minPrice, Double maxPrice, Long storeId) {
        List<ProductEntity> productEntities = productRepository.getProductsFilter(productName, categoryName, minPrice, maxPrice, storeId);

        if(CollectionUtils.isEmpty(productEntities)){
            throw new NotFoundException("No hay ningun producto registrado en la aplicación");
        }
        return productEntityProductDTOMapper.productEntityListToProductDTOList(productEntities);
    }

    public ProductDTO updateProductById(Long productId, ProductRequestDTO productRequestDTO) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(productId);
        ProductEntity productEntity = new ProductEntity();
        if (productEntityOptional.isPresent()){
            productEntity = productEntityOptional.get();
            productEntity.setProductName(productRequestDTO.getProductName());
            productEntity.setPrice(productRequestDTO.getPrice());
            productEntity.setCategory(categoryService.getCategoryEntityById(productRequestDTO.getCategoryId()));
            productEntity.setStoreId(productRequestDTO.getStoreId());
            productRepository.save(productEntity);
        } else {
            throw new NotFoundException("No existe una producto con el id indicado");
        }
        return productEntityProductDTOMapper.productEntityToProductDTO(productEntity);
    }

    public List<ProductDTO> getProductByStoreId(Long storeId) {
        List<ProductEntity> productEntities = productRepository.getProductByStoreId(storeId);
        if(CollectionUtils.isEmpty(productEntities)){
            throw new NotFoundException("No hay productos para la store seleccionada.");
        }
        return productEntityProductDTOMapper.productEntityListToProductDTOList(productEntities);
    }

    public Page<ProductDTO> getPagedProductsFilter(String productName, String categoryName, Double minPrice, Double maxPrice, Long storeId, Pageable pageable) {
        Page<ProductEntity> productEntitiesPaged = (Page<ProductEntity>) productRepository.getPagedProductsFilter(productName, categoryName, minPrice, maxPrice, storeId, pageable);

        if(CollectionUtils.isEmpty(productEntitiesPaged.getContent())){
            throw new NotFoundException("No hay ningun producto registrado en la aplicación");
        }

        return productEntitiesPaged.map(productEntityProductDTOMapper::productEntityToProductDTO);
    }
}
