package org.mcs.productv1service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.mcs.productv1service.dto.ProductRequestDto;
import org.mcs.productv1service.dto.ProductResponseDto;
import org.mcs.productv1service.service.ProductService;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/get/{productId}")
    @Operation(summary = "получение продукта по id")
    public ProductResponseDto getProductById(@Parameter(description = "id продукта") @PathVariable("productId") Long productId) {
        return productService.getProductById(productId);
    }

    @GetMapping("/get/all/{productIds}")
    @Operation(summary = "получение списка продуктов по id")
    public List<ProductResponseDto> getAllProductsByIds(@Parameter(description = "список id продуктов") @PathVariable("productIds") List<Long> productsIds) {
        return productService.getAllProductsByIds(productsIds);
    }

    @PostMapping("/add")
    @Operation(summary = "добавление продукта")
    public void addProduct(@RequestBody ProductRequestDto productRequestDto) {
        productService.addProduct(productRequestDto);
    }

    @DeleteMapping("/delete/{productId}")
    @Operation(summary = "удаление продукта по id")
    public void deleteProductById(@Parameter(description = "id продукта") @PathVariable("productId") Long productId) {
        productService.deleteProductById(productId);
    }

    @PutMapping("/update/{productId}")
    @Operation(summary = "обновление продукта по id")
    public void updateProductById(@Parameter(description = "id продукта") @PathVariable("productId") Long productId, @RequestBody ProductRequestDto productRequestDto) {
        productService.updateProductById(productId, productRequestDto);
    }
}
