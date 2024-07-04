package org.mcs.productv1service.service;

import lombok.RequiredArgsConstructor;
import org.mcs.productv1service.dto.ProductRequestDto;
import org.mcs.productv1service.dto.ProductResponseDto;
import org.mcs.productv1service.exception.ExceptionMessage;
import org.mcs.productv1service.exception.RestException;
import org.mcs.productv1service.mapper.ProductMapper;
import org.mcs.productv1service.model.Product;
import org.mcs.productv1service.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Override
    public ProductResponseDto getProductById(Long productId) {
        Product product = getProductOrElseThrow(productId);
        ProductResponseDto productResponseDto = productMapper.mapProductToProductResponse(product);
        return productResponseDto;
    }

    @Override
    public List<ProductResponseDto> getAllProductsByIds(List<Long> productIds) {
        List<Product> products = productIds.stream()
                .map(this::getProductOrElseThrow)
                .toList();
        List<ProductResponseDto> productResponseDtos = productMapper.mapProductsToProductResponse(products);
        return productResponseDtos;
    }

    @Override
    @Transactional
    public void addProduct(ProductRequestDto productRequestDto) {
        Product product = productMapper.mapProductRequestToProduct(productRequestDto);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProductById(Long productId) {
        Product product = getProductOrElseThrow(productId);
        productRepository.delete(product);
    }

    @Override
    @Transactional
    public void updateProductById(Long productId, ProductRequestDto productRequestDto) {
        Product product = getProductOrElseThrow(productId);
        Product updatedProduct = productMapper.updateProduct(product, productRequestDto);
        productRepository.save(updatedProduct);
    }

    private Product getProductOrElseThrow(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(() -> new RestException(ExceptionMessage.NULL_POINTER.getMessage(), productId));
    }
}
