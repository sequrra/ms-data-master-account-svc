package com.ms.data.master.account.model.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDetailsDTO {
    private String companyName;
    private String companyAddress;
    private String registrationNumber;
    private String website;
    private String country;
    private String state;
    private String city;
}
