package org.mcs.authservice.repository;

import org.mcs.authservice.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findUserEntityByUsername(String username);

    boolean existsUserEntityByEmail(String email);
}
