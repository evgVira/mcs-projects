package org.mcs.userv1service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.mcs.userv1service.dto.ProductResponseDto;
import org.mcs.userv1service.dto.UserRequestDto;
import org.mcs.userv1service.dto.UserResponseDto;
import org.mcs.userv1service.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    @Operation(summary = "зарегистриовать пользователя")
    public void registrationUser(@RequestBody UserRequestDto userRequestDto){
        userService.registrationUser(userRequestDto);
    }

    @GetMapping("/get/{userId}")
    @Operation(summary = "получить пользователя по id")
    public UserResponseDto getUserById(@Parameter(name = "id пользователя") @PathVariable("userId") Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/get/all/{userIds}")
    @Operation(summary = "получить список пользователей по списку id")
    public List<UserResponseDto> getAllUsersById(@Parameter(name = "список id пользователей") @PathVariable("userIds") List<Long> userIds) {
        return userService.getAllUsersByIds(userIds);
    }


    @PutMapping("/update/{userId}")
    @Operation(summary = "обновить данные о пользователе")
    public void updateUserById(@Parameter(name = "обновить данные о пользователе по id") @PathVariable("userId") Long userId, @RequestBody UserRequestDto userRequestDto) {
        userService.updateUserById(userId, userRequestDto);
    }

    @DeleteMapping("/delete/{userId}")
    @Operation(summary = "удалить пользователя по id")
    public void deleteUserById(@Parameter(name = "id пользователя") @PathVariable("userId") Long userId) {
        userService.deleteUserById(userId);
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "получить продукт по id")
    public ProductResponseDto getProductById(@Parameter(name = "id продукта") @PathVariable("productId") Long productId) {
        return userService.getProductById(productId);
    }

    @GetMapping("/get/all")
    public List<UserResponseDto> getAllUsers(){
        return userService.getAllUsers();
    }

}
