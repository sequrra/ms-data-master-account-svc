package com.ms.data.master.account.controller;

import com.ms.data.master.account.constant.PathConstant;
import com.ms.data.master.account.exception.AccountExceptionHandler;
import com.ms.data.master.account.model.Account;
import com.ms.data.master.account.model.dto.account.AccountDTO;
import com.ms.data.master.account.model.dto.response.PageResponse;
import com.ms.data.master.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(PathConstant.ACCOUNT_CONTROLLER)
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService accountService;

    @Value("${common.pageable.size}")
    private Integer pageableSize;

    @Value("${common.pageable.page}")
    private Integer pageablePage;

    @Value("${common.sorting}")
    private String sortingPage;

    @GetMapping(value = PathConstant.GET_ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<PageResponse<AccountDTO>> getAllAccounts(
            @RequestParam(value = "pageableSize", required = false) Integer defaultPageableSize,
            @RequestParam(value = "pageablePage", required = false) Integer defaultPageablePage,
            @ModelAttribute AccountDTO accountDTO,
            @SortDefault(sort = "id", direction = Sort.Direction.ASC) Sort sorting) throws AccountExceptionHandler {

        return ResponseEntity.ok(accountService.getAllService(
                Optional.ofNullable(defaultPageableSize).filter(size -> size > 0).orElse(pageableSize),
                Optional.ofNullable(defaultPageablePage).filter(page -> page >= 0).orElse(pageablePage),
                Optional.ofNullable(sorting).orElse(Sort.by(Sort.Direction.fromString(sortingPage), "id")),
                accountDTO
        ));
    }

    @GetMapping(value = PathConstant.GET_ID + PathConstant.ID_PARAMETER, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<AccountDTO> getIdAccounts(@PathVariable UUID id) {
        return ResponseEntity.ok(accountService.getIdService(id));
    }

    @PutMapping(value = PathConstant.UPDATE + PathConstant.GET_ID + PathConstant.ID_PARAMETER,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('USER')")
    public ResponseEntity<AccountDTO> updateAccounts(@PathVariable UUID id,
                                                     @RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(accountService.updateService(id, accountDTO));
    }

    @DeleteMapping(value = PathConstant.DELETE + PathConstant.GET_ID + PathConstant.ID_PARAMETER,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteAccounts(@PathVariable UUID id) {
            accountService.deleteService(id);
            return ResponseEntity.ok().build();
    }

    @PostMapping(value = PathConstant.CREATE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<AccountDTO> createAccounts(@RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(accountService.createService(accountDTO));
    }

    @GetMapping(value = PathConstant.PROFILE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> getProfile(Authentication authentication) {
        return ResponseEntity.ok(accountService.getIdService(extractUserIdFromAuthentication(authentication)));
    }

    @PutMapping(value = PathConstant.PROFILE + PathConstant.UPDATE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> updateProfile(Authentication authentication,
                                                    @RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(accountService.updateService(extractUserIdFromAuthentication(authentication), accountDTO));
    }


    private UUID extractUserIdFromAuthentication(Authentication authentication) {
        return Optional.of(authentication.getPrincipal())
                .filter(Account.class::isInstance)
                .map(Account.class::cast)
                .map(Account::getId)
                .orElseThrow(() -> new ClassCastException("Authentication principal is not of type Account"));
    }


}
