package org.mcs.productv1service.service;

import org.mcs.productv1service.dto.ProductRequestDto;
import org.mcs.productv1service.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {

    ProductResponseDto getProductById(Long productId);

    List<ProductResponseDto> getAllProductsByIds(List<Long> productIds);

    void addProduct(ProductRequestDto productRequestDto);

    void deleteProductById(Long productId);

    void updateProductById(Long productId, ProductRequestDto productRequestDto);
}
