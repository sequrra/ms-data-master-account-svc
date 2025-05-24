package com.ms.data.master.account.model.dto.authtentication.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TokenRefreshResponseDTO {
    private String accessToken;
    private String refreshToken;
    private Boolean isValid;
    private String username;
    private String accountType;
    private String message;
}
