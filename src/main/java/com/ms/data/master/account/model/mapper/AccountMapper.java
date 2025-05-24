package com.ms.data.master.account.model.mapper;

import com.ms.data.master.account.model.Account;
import com.ms.data.master.account.model.dto.account.AccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    Account toEntity(AccountDTO accountDTO);

    @Mapping(source = "role", target = "rolesDetails")
    AccountDTO toDTO(Account account);

    @Mapping(source = "rolesDetails", target = "role")
    void updateFromDTOToEntity(AccountDTO accountDTO, @MappingTarget Account account);

}