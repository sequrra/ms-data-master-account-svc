package com.ms.data.master.account.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "endpoint.authentication")
@Getter
@Setter
public class AuthenticationEndpointProperties {
    private String base;
    private String signup;
    private String login;
    private String refreshToken;
    private String forgotPassword;
    private String resetPassword;
    private String oauthGoogle;
}
