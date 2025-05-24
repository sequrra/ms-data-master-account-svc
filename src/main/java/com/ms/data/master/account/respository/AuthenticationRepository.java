package com.ms.data.master.account.respository;

import com.ms.data.master.account.model.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface AuthenticationRepository extends CrudRepository<Account, UUID> {
    Optional<Account> findByEmail(String email);
}