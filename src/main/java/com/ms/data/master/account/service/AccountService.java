package com.ms.data.master.account.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.data.master.account.model.Account;
import com.ms.data.master.account.model.Roles;
import com.ms.data.master.account.model.dto.account.AccountDTO;
import com.ms.data.master.account.model.dto.account.RolesDTO;
import com.ms.data.master.account.model.dto.response.PageResponse;
import com.ms.data.master.account.model.mapper.AccountMapper;
import com.ms.data.master.account.respository.AccountRepository;
import com.ms.data.master.account.respository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public PageResponse<AccountDTO> getAllService(Integer pageableSize, Integer pageablePage, Sort sorting, AccountDTO accountDTO)
    {
        return
                new PageResponse<>(
                        getAllFromRepository(pageableSize, pageablePage, sorting, accountDTO)
                                .getContent()
                                .stream()
                                .map(AccountMapper.INSTANCE::toDTO)
                                .collect(Collectors.toList()),
                        getAllFromRepository(pageableSize, pageablePage, sorting, accountDTO).getTotalElements(),
                        getAllFromRepository(pageableSize, pageablePage, sorting, accountDTO).getSize(),
                        getAllFromRepository(pageableSize, pageablePage, sorting, accountDTO).getNumber() + 1

        );
    }



    public AccountDTO getIdService(UUID id) {
        return Optional.ofNullable(getIdFromRepository(id))
                .map(AccountMapper.INSTANCE::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Account not found for id: " + id));
    }

    @Transactional
    public AccountDTO createService(AccountDTO accountDTO) {
        return AccountMapper.INSTANCE.toDTO(
                accountRepository.save(
                        setRoleAndEncodePassword(
                                AccountMapper.INSTANCE.toEntity(accountDTO),
                                accountDTO
                        )
                )
        );
    }

    @Transactional
    public AccountDTO updateService(UUID id, AccountDTO accountDTO) {
        return accountRepository.findById(id)
                .map(account -> {
                    if (Objects.nonNull(accountDTO.getRolesDetails()) && Objects.nonNull(accountDTO.getRolesDetails().getId())) {
                        account.setRole(roleRepository.findById(accountDTO.getRolesDetails().getId())
                                .orElseThrow(() -> new EntityNotFoundException("Role not found for id: " + accountDTO.getRolesDetails().getId())));
                    }
                    if (Objects.nonNull(accountDTO.getPassword())) {
                        accountDTO.setPassword(encodePassword(accountDTO.getPassword()));
                    }
                    AccountMapper.INSTANCE.updateFromDTOToEntity(accountDTO, account);
                    return AccountMapper.INSTANCE.toDTO(accountRepository.save(account));
                })
                .orElseThrow(() -> new EntityNotFoundException("Account not found for id: " + id));
    }



    public void deleteService(UUID id) {
        deleteFromRepository(id);
    }



    private Page<Account> getAllFromRepository(Integer pageableSize, Integer pageablePage, Sort sorting, AccountDTO accountDTO) {
        return accountRepository.findAll(buildSpecification(accountDTO), PageRequest.of(pageablePage, pageableSize, sorting));
    }

    private Account getIdFromRepository(UUID id) {
        return accountRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Account not found for id: " + id));
    }

    private void deleteFromRepository(UUID id) {
        accountRepository.deleteById(id);
    }

    private Specification<Account> buildSpecification(AccountDTO accountDTO) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(accountDTO.getAccountType())) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("accountType")),
                        accountDTO.getAccountType().toLowerCase()
                ));
            }


            // Return an empty predicate if no conditions are applied
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private void setRole(Account account, RolesDTO rolesDetails) {
        if (rolesDetails != null && rolesDetails.getId() != null) {
            Roles role = roleRepository.findById(rolesDetails.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + rolesDetails.getId()));
            account.setRole(role);
        }
    }


    private Account setRoleAndEncodePassword(Account account, AccountDTO accountDTO) {
        setRole(account, accountDTO.getRolesDetails());
        account.setPassword(encodePassword(accountDTO.getPassword()));
        return account;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }


}