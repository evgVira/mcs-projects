package org.mcs.userv1service.service;

import lombok.RequiredArgsConstructor;
import org.mcs.userv1service.client.ProductClient;
import org.mcs.userv1service.dto.ProductResponseDto;
import org.mcs.userv1service.dto.UserRequestDto;
import org.mcs.userv1service.dto.UserResponseDto;
import org.mcs.userv1service.exception.ExceptionMessage;
import org.mcs.userv1service.exception.RestException;
import org.mcs.userv1service.mapper.UserEntityMapper;
import org.mcs.userv1service.model.UserEntity;
import org.mcs.userv1service.repository.UserEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;

    private final UserEntityMapper userEntityMapper;

    private final ProductClient productClient;

    @Override
    public UserResponseDto getUserById(Long userId) {
        UserEntity userEntity = getUserEntityOrThrow(userId);
        UserResponseDto userResponseDto = userEntityMapper.mapUserEntityToUserResponse(userEntity);
        return userResponseDto;
    }

    @Override
    public List<UserResponseDto> getAllUsersByIds(List<Long> userIds) {

        List<UserEntity> userEntities = userIds.stream()
                .map(this::getUserEntityOrThrow)
                .toList();
        List<UserResponseDto> userResponseDtos = userEntityMapper.mapUserEntitiesToUserResponse(userEntities);

        return userResponseDtos;
    }

    @Override
    @Transactional
    public void createUser(UserRequestDto userRequestDto) {
        UserEntity userEntity = userEntityMapper.mapUserRequestToUserEntity(userRequestDto);
        userEntityRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void updateUserById(Long userId, UserRequestDto userRequestDto) {
        UserEntity userEntity = getUserEntityOrThrow(userId);
        UserEntity updatedUser = userEntityMapper.updateUserEntity(userEntity, userRequestDto);
        userEntityRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void deleteUserById(Long userId) {
        UserEntity userEntity = getUserEntityOrThrow(userId);
        userEntityRepository.delete(userEntity);
    }

    @Override
    @Transactional
    public ProductResponseDto getProductById(Long productId) {
        return productClient.getProductById(productId);
    }

    private UserEntity getUserEntityOrThrow(Long userId) {
        UserEntity userEntity = userEntityRepository.findById(userId)
                .orElseThrow(() -> new RestException(ExceptionMessage.NULL_POINTER.getMessage()));
        return userEntity;
    }


}
