package com.ms.data.master.account.model.mapper;

import com.ms.data.master.account.model.Account;
import com.ms.data.master.account.model.Roles;
import com.ms.data.master.account.model.dto.account.AccountDTO;
import com.ms.data.master.account.model.dto.account.RolesDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-03T14:31:12+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (JetBrains s.r.o.)"
)
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account toEntity(AccountDTO accountDTO) {
        if ( accountDTO == null ) {
            return null;
        }

        Account.AccountBuilder<?, ?> account = Account.builder();

        account.id( accountDTO.getId() );
        account.accountName( accountDTO.getAccountName() );
        account.firstName( accountDTO.getFirstName() );
        account.lastName( accountDTO.getLastName() );
        account.accountType( accountDTO.getAccountType() );
        account.roleType( accountDTO.getRoleType() );
        account.email( accountDTO.getEmail() );
        account.website( accountDTO.getWebsite() );
        account.password( accountDTO.getPassword() );
        account.phoneNumber( accountDTO.getPhoneNumber() );
        account.location( accountDTO.getLocation() );
        account.isActive( accountDTO.getIsActive() );
        account.billingDetails( accountDTO.getBillingDetails() );
        account.companyDetails( accountDTO.getCompanyDetails() );
        account.creditTermDetails( accountDTO.getCreditTermDetails() );
        account.businessDetails( accountDTO.getBusinessDetails() );
        account.version( accountDTO.getVersion() );
        account.createdBy( accountDTO.getCreatedBy() );
        account.createdAt( accountDTO.getCreatedAt() );
        account.updatedBy( accountDTO.getUpdatedBy() );
        account.updatedAt( accountDTO.getUpdatedAt() );
        account.deletedBy( accountDTO.getDeletedBy() );
        account.deletedAt( accountDTO.getDeletedAt() );

        return account.build();
    }

    @Override
    public AccountDTO toDTO(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountDTO accountDTO = new AccountDTO();

        accountDTO.setRolesDetails( rolesToRolesDTO( account.getRole() ) );
        accountDTO.setId( account.getId() );
        accountDTO.setAccountName( account.getAccountName() );
        accountDTO.setFirstName( account.getFirstName() );
        accountDTO.setLastName( account.getLastName() );
        accountDTO.setAccountType( account.getAccountType() );
        accountDTO.setRoleType( account.getRoleType() );
        accountDTO.setEmail( account.getEmail() );
        accountDTO.setWebsite( account.getWebsite() );
        accountDTO.setPassword( account.getPassword() );
        accountDTO.setPhoneNumber( account.getPhoneNumber() );
        accountDTO.setLocation( account.getLocation() );
        accountDTO.setBillingDetails( account.getBillingDetails() );
        accountDTO.setCompanyDetails( account.getCompanyDetails() );
        accountDTO.setCreditTermDetails( account.getCreditTermDetails() );
        accountDTO.setBusinessDetails( account.getBusinessDetails() );
        accountDTO.setIsActive( account.getIsActive() );
        accountDTO.setVersion( account.getVersion() );
        accountDTO.setCreatedBy( account.getCreatedBy() );
        accountDTO.setCreatedAt( account.getCreatedAt() );
        accountDTO.setUpdatedBy( account.getUpdatedBy() );
        accountDTO.setUpdatedAt( account.getUpdatedAt() );
        accountDTO.setDeletedBy( account.getDeletedBy() );
        accountDTO.setDeletedAt( account.getDeletedAt() );

        return accountDTO;
    }

    @Override
    public void updateFromDTOToEntity(AccountDTO accountDTO, Account account) {
        if ( accountDTO == null ) {
            return;
        }

        if ( accountDTO.getRolesDetails() != null ) {
            if ( account.getRole() == null ) {
                account.setRole( Roles.builder().build() );
            }
            rolesDTOToRoles( accountDTO.getRolesDetails(), account.getRole() );
        }
        else {
            account.setRole( null );
        }
        account.setId( accountDTO.getId() );
        account.setAccountName( accountDTO.getAccountName() );
        account.setFirstName( accountDTO.getFirstName() );
        account.setLastName( accountDTO.getLastName() );
        account.setAccountType( accountDTO.getAccountType() );
        account.setRoleType( accountDTO.getRoleType() );
        account.setEmail( accountDTO.getEmail() );
        account.setWebsite( accountDTO.getWebsite() );
        account.setPassword( accountDTO.getPassword() );
        account.setPhoneNumber( accountDTO.getPhoneNumber() );
        account.setLocation( accountDTO.getLocation() );
        account.setIsActive( accountDTO.getIsActive() );
        account.setBillingDetails( accountDTO.getBillingDetails() );
        account.setCompanyDetails( accountDTO.getCompanyDetails() );
        account.setCreditTermDetails( accountDTO.getCreditTermDetails() );
        account.setBusinessDetails( accountDTO.getBusinessDetails() );
        account.setVersion( accountDTO.getVersion() );
        account.setCreatedBy( accountDTO.getCreatedBy() );
        account.setCreatedAt( accountDTO.getCreatedAt() );
        account.setUpdatedBy( accountDTO.getUpdatedBy() );
        account.setUpdatedAt( accountDTO.getUpdatedAt() );
        account.setDeletedBy( accountDTO.getDeletedBy() );
        account.setDeletedAt( accountDTO.getDeletedAt() );
    }

    protected RolesDTO rolesToRolesDTO(Roles roles) {
        if ( roles == null ) {
            return null;
        }

        RolesDTO rolesDTO = new RolesDTO();

        rolesDTO.setId( roles.getId() );
        rolesDTO.setRolesName( roles.getRolesName() );
        rolesDTO.setRolesDescription( roles.getRolesDescription() );
        rolesDTO.setVersion( roles.getVersion() );
        rolesDTO.setStatus( roles.getStatus() );
        rolesDTO.setCreatedBy( roles.getCreatedBy() );
        rolesDTO.setCreatedAt( roles.getCreatedAt() );
        rolesDTO.setUpdatedBy( roles.getUpdatedBy() );
        rolesDTO.setUpdatedAt( roles.getUpdatedAt() );
        rolesDTO.setDeletedBy( roles.getDeletedBy() );
        rolesDTO.setDeletedAt( roles.getDeletedAt() );

        return rolesDTO;
    }

    protected void rolesDTOToRoles(RolesDTO rolesDTO, Roles mappingTarget) {
        if ( rolesDTO == null ) {
            return;
        }

        mappingTarget.setId( rolesDTO.getId() );
        mappingTarget.setRolesName( rolesDTO.getRolesName() );
        mappingTarget.setRolesDescription( rolesDTO.getRolesDescription() );
        mappingTarget.setVersion( rolesDTO.getVersion() );
        mappingTarget.setStatus( rolesDTO.getStatus() );
        mappingTarget.setCreatedBy( rolesDTO.getCreatedBy() );
        mappingTarget.setCreatedAt( rolesDTO.getCreatedAt() );
        mappingTarget.setUpdatedBy( rolesDTO.getUpdatedBy() );
        mappingTarget.setUpdatedAt( rolesDTO.getUpdatedAt() );
        mappingTarget.setDeletedBy( rolesDTO.getDeletedBy() );
        mappingTarget.setDeletedAt( rolesDTO.getDeletedAt() );
    }
}
