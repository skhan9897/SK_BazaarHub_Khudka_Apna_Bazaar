package com.skbazaarhub.auth.repository;

import com.skbazaarhub.auth.entity.RoleEntity;
import com.skbazaarhub.common.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(RoleName name);
}
