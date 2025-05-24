package com.ms.data.master.account.controller;

import com.ms.data.master.account.constant.PathConstant;
import com.ms.data.master.account.model.Account;
import com.ms.data.master.account.model.dto.authtentication.request.LoginRequestDTO;
import com.ms.data.master.account.model.dto.authtentication.request.RegisterRequestDTO;
import com.ms.data.master.account.model.dto.authtentication.response.LoginResponseDTO;
import com.ms.data.master.account.model.dto.authtentication.response.TokenRefreshResponseDTO;
import com.ms.data.master.account.service.AuthenticationService;
import com.ms.data.master.account.service.JWTService;
//import com.ms.data.master.account.service.event.AuthEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("${endpoint.authentication.base}")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final JWTService jwtService;

    private final AuthenticationService authenticationService;

//    private final AuthEventProducer authEventProducer;



    @PostMapping("${endpoint.authentication.signup}")
    public ResponseEntity<Account> register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        return ResponseEntity.ok(authenticationService.signup(registerRequestDTO));
    }

    @GetMapping("/login/oauth2/code/google")
    public String oauth2Callback(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return "OAuth2 authentication failed";
        }

        System.out.println("User Name: " + principal.getAttribute("name"));
        System.out.println("Email: " + principal.getAttribute("email"));

        return "Login success";
    }

    @PostMapping("${endpoint.authentication.oauth-google}")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> payload) {
        return authenticationService.authenticateWithGoogle(payload.get("token"));
    }

    @PostMapping("${endpoint.authentication.login}")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody LoginRequestDTO loginRequestDTO) {
        var authenticatedUser = authenticationService.authenticate(loginRequestDTO);
        var token = jwtService.generateToken(authenticatedUser);
//        authEventProducer.sendAuthEvent(String.valueOf(authenticatedUser.getId()), token, loginRequestDTO.getEmail(), authenticatedUser.getAccountType());

        return ResponseEntity.ok(new LoginResponseDTO()
                .setId(authenticatedUser.getId())
                .setToken(token)
                .setExpiresIn(jwtService.getExpirationTime())
                .setAccountType(authenticatedUser.getAccountType())
                .setEmail(loginRequestDTO.getEmail()));
    }


    @PostMapping("${endpoint.authentication.refresh-token}")
    public ResponseEntity<TokenRefreshResponseDTO> verifyToken(@RequestHeader("Authorization") String refreshToken) {
        return refreshToken.startsWith("Bearer ") && jwtService.validateRefreshToken(refreshToken.substring(7)) ?
                ResponseEntity.ok(new TokenRefreshResponseDTO()
                        .setAccessToken(refreshToken.substring(7))
                        .setRefreshToken(jwtService.generateAccessToken(jwtService.extractUsername(refreshToken.substring(7)),
                                jwtService.extractAccountType(refreshToken.substring(7))))
                        .setMessage("New access token generated successfully"))
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new TokenRefreshResponseDTO().setMessage("Invalid or expired refresh token"));
    }

    @PostMapping("${endpoint.authentication.forgot-password}")
    public ResponseEntity<String> forgotPassword(@RequestBody String email) {
        authenticationService.sendPasswordResetEmail(email);
        return ResponseEntity.ok("Password reset email sent successfully, if the email exists in our system.");
    }

    @PostMapping("${endpoint.authentication.reset-password}")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> requestBody) {
        String token = requestBody.get("token");
        String newPassword = requestBody.get("newPassword");

        if (!jwtService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }

        String email = jwtService.extractUsername(token);
        authenticationService.updatePassword(email, newPassword);

        return ResponseEntity.ok("Password reset successfully.");
    }




}