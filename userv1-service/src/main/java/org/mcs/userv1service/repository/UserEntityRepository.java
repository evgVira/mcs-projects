package org.mcs.userv1service.repository;

import org.mcs.userv1service.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserEntityRepository extends CrudRepository<UserEntity, Long> {
    boolean existsUserEntityByEmail(String email);

    List<UserEntity> findAll();
}
