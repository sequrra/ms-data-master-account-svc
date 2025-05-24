package com.ms.data.master.account.model.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDetailsDTO {
    private String companyName;
    private String companyAddress;
    private String email;
    private String phoneNumber;
    private String taxNumber;
}

