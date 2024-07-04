package org.mcs.userv1service.mapper;

import org.mapstruct.*;
import org.mcs.userv1service.dto.UserRequestDto;
import org.mcs.userv1service.dto.UserResponseDto;
import org.mcs.userv1service.model.UserEntity;

import java.time.Instant;
import java.util.List;

@Mapper(componentModel = "spring", imports = {Instant.class})
public interface UserEntityMapper {

    @Mapping(target = "createdDt", defaultExpression = "java(Instant.now().toString())", dateFormat = "dd/mm/yy/hh/mm/ss")
    @Mapping(target = "updatedDt", defaultExpression = "java(Instant.now().toString())", dateFormat = "dd/mm/yy/hh/mm/ss")
    UserResponseDto mapUserEntityToUserResponse(UserEntity userEntity);

    @InheritConfiguration
    List<UserResponseDto> mapUserEntitiesToUserResponse(List<UserEntity> userEntities);

    @Mapping(target = "createdDt", expression = "java(Instant.now())", dateFormat = "dd/mm/yy/hh/mm/ss")
    @Mapping(target = "updatedDt", expression = "java(Instant.now())", dateFormat = "dd/mm/yy/hh/mm/ss")
    UserEntity mapUserRequestToUserEntity(UserRequestDto userRequestDto);

    @Mapping(target = "username", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
    @Mapping(target = "email", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
    @Mapping(target = "password", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
    @Mapping(target = "updatedDt", expression = "java(Instant.now())", dateFormat = "dd/mm/yy/hh/mm/ss")
    UserEntity updateUserEntity(@MappingTarget UserEntity userEntity, UserRequestDto userRequestDto);

}
