package com.ms.data.master.account.respository;

import com.ms.data.master.account.model.Roles;
import com.ms.data.master.account.model.RolesEnum;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends CrudRepository<Roles, Integer> {
    Optional<Roles> findByRolesName(RolesEnum rolesName);
    Optional<Roles> findById(UUID id);
}