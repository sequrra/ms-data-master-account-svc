package com.ms.data.master.account.model.dto.authtentication.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    private String email;
    private String password;
    private String accountName;
    private String accountType;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String role;
}
