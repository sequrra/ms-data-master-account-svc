package com.ms.data.master.account.model.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDetailsDTO {
    private String accountNumber;
    private String accountName;
    private String accountType;
    private String bankName;
    private String branchCode;
}
