package com.ms.data.master.account.model.dto.authtentication.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LoginResponseDTO {
    private UUID id;
    private String email;
    private String accountType;
    private String token;
    private Long expiresIn;
}
