package org.mcs.userv1service.service;

import org.mcs.userv1service.dto.ProductResponseDto;
import org.mcs.userv1service.dto.UserRequestDto;
import org.mcs.userv1service.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto getUserById(Long userId);

    List<UserResponseDto> getAllUsersByIds(List<Long> userIds);

    void createUser(UserRequestDto userRequestDto);

    void updateUserById(Long userId, UserRequestDto userRequestDto);

    void deleteUserById(Long userId);

    ProductResponseDto getProductById(Long productId);
}
