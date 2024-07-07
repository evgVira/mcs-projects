package org.mcs.authservice.service;

import org.mcs.authservice.dto.UserResponseDto;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/api/v1/user/get/all")
    @LoadBalanced
    List<UserResponseDto> getAllUsers();
}
