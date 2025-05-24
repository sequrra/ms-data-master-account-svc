package com.ms.data.master.account.model.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingDetailsDTO {
    private String companyName;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String contactAddress;
    private Boolean isCompanyDetailCopy;
}
