package org.mcs.authservice.service;

import lombok.RequiredArgsConstructor;
import org.mcs.authservice.dto.UserResponseDto;
import org.mcs.authservice.model.UserEntity;
import org.mcs.authservice.repository.UserEntityRepository;
import org.mcs.authservice.security.config.PasswordEncoderConfig;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthenticationService {

    private final UserEntityRepository userEntityRepository;

    private final UserServiceClient userServiceClient;

    @Override
    public void updateInfoByUser() {
        List<UserResponseDto> userResponseDtos = userServiceClient.getAllUsers();

        List<UserEntity> userEntities = userResponseDtos.stream()
                .filter(userResponseDto -> !userEntityRepository.existsUserEntityByEmail(userResponseDto.getEmail()))
                .map(userResponseDto -> {
                    return UserEntity.builder()
                            .username(userResponseDto.getUsername())
                            .email(userResponseDto.getEmail())
                            .password(userResponseDto.getPassword())
                            .build();
                }).toList();

        userEntityRepository.saveAll(userEntities);

    }
}
