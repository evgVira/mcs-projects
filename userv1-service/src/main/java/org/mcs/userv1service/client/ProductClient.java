package org.mcs.userv1service.client;

import org.mcs.userv1service.dto.ProductResponseDto;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/api/v1/product/get/{productId}")
    @LoadBalanced
    ProductResponseDto getProductById(@PathVariable("productId") Long productId);
}
