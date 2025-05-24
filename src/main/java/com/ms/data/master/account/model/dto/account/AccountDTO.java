package com.ms.data.master.account.model.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private UUID id;
    private String accountName;
    private String firstName;
    private String lastName;
    private String accountType;
    private String roleType;
    private String email;
    private String website;
    private String password;
    private String phoneNumber;
    private String location;
    private BillingDetailsDTO billingDetails;
    private CompanyDetailsDTO companyDetails;
    private CreditTermDetailsDTO creditTermDetails;
    private BusinessDetailsDTO businessDetails;
    private RolesDTO rolesDetails;
    private Boolean isActive;
    private Integer version;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
    private String deletedBy;
    private LocalDateTime deletedAt;
}
