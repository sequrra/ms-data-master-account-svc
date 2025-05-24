package com.ms.data.master.account.model.dto.account;

import com.ms.data.master.account.model.RolesEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolesDTO {
    private UUID id;
    private RolesEnum rolesName;
    private String rolesDescription;
    private Integer version;
    private String status;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
    private String deletedBy;
    private LocalDateTime deletedAt;
}
