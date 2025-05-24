package com.ms.data.master.account.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.ms.data.master.account.properties.GoogleOAuthProperties;
import com.ms.data.master.account.model.Account;
import com.ms.data.master.account.model.Roles;
import com.ms.data.master.account.model.RolesEnum;
import com.ms.data.master.account.model.dto.authtentication.request.LoginRequestDTO;
import com.ms.data.master.account.model.dto.authtentication.request.RegisterRequestDTO;
import com.ms.data.master.account.respository.AuthenticationRepository;
import com.ms.data.master.account.respository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationRepository authenticationRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final GoogleOAuthProperties googleOAuthProperties;

    public Account signup(RegisterRequestDTO registerRequestDTO) {
        Optional<Roles> optionalRole = roleRepository.findByRolesName(RolesEnum.valueOf(registerRequestDTO.getRole()));

        if (optionalRole.isEmpty()) {
            return null;
        }

        var user = new Account()
                .setFirstName(registerRequestDTO.getFirstName())
                .setLastName(registerRequestDTO.getLastName())
                .setAccountType(registerRequestDTO.getAccountType())
                .setEmail(registerRequestDTO.getEmail())
                .setAccountName(registerRequestDTO.getAccountName())
                .setPhoneNumber(registerRequestDTO.getPhoneNumber())
                .setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .setRole(optionalRole.get());

        return authenticationRepository.save(user);
    }

    public Account authenticate(LoginRequestDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return authenticationRepository.findByEmail(input.getEmail()).orElseThrow();
    }

    public ResponseEntity<?> authenticateWithGoogle(String idToken) {
        return Optional.ofNullable(verifyGoogleToken(idToken))
                .map(payload -> {
                    String email = payload.getEmail();
                    String firstName = (String) payload.get("given_name");
                    String lastName = (String) payload.get("family_name");

                    Account account = authenticationRepository.findByEmail(email).orElseGet(() -> {
                        Roles defaultRole = roleRepository.findByRolesName(RolesEnum.USER)
                                .orElseThrow(() -> new RuntimeException("Default USER role not found"));

                        Account newAccount = new Account()
                                .setAccountName(firstName + " " + lastName)
                                .setEmail(email)
                                .setFirstName(firstName)
                                .setLastName(lastName)
                                .setPhoneNumber("00000000") // ✅ Required: Dummy or default value
                                .setPassword(passwordEncoder.encode("oauth2-user"))
                                .setRole(defaultRole);

                        return authenticationRepository.save(newAccount);
                    });

                    String jwt = jwtService.generateToken(account);
                    return ResponseEntity.ok(Map.of("token", jwt));
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid Google token")));
    }

    private GoogleIdToken.Payload verifyGoogleToken(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), GsonFactory.getDefaultInstance())
                    .setAudience(List.of(googleOAuthProperties.getClientId()))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken == null) {
                System.err.println("ID token could not be verified.");
                return null;
            }

            System.out.println("Token verified. Payload: " + idToken.getPayload());
            return idToken.getPayload();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private boolean isValidPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }


    public void sendPasswordResetEmail(String email) {
        var account = authenticationRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No account found with email: " + email));

        // Generate reset token
        String resetToken = jwtService.generateToken(account);

        // Log or store the reset token if needed for validation
        // Example: Save token in the database or an in-memory store like Redis (not shown here).

        // Send email
        sendEmail(account.getEmail(), "Password Reset Request",
                "You can reset your password using the following link: " +
                        "http://ugkslimousine.com/reset-password?token=" + resetToken);
    }

    private void sendEmail(String to, String subject, String body) {
        // Implementation for sending an email
        // Consider using libraries like JavaMailSender or external services (SendGrid, AWS SES, etc.)
        System.out.println("Sending email to: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
    }

    public void updatePassword(String email, String newPassword) {
        var account = authenticationRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No account found with email: " + email));

        account.setPassword(passwordEncoder.encode(newPassword));
        authenticationRepository.save(account);
    }








}