package com.ms.data.master.account.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

@Configuration
public class LinodeS3Config {

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.access-key}")
    private String accessKey;

    @Value("${oss.secret-key}")
    private String secretKey;

    @Value("${oss.bucket}")
    private String bucket;

    @Bean
    public S3Client s3Client() {
        // Ensure the endpoint starts with http/https
        if (!endpoint.startsWith("http://") && !endpoint.startsWith("https://")) {
            endpoint = "https://" + endpoint;  // Default to https if no scheme is provided
        }

        // Create the S3 configuration
        S3Configuration s3Config = S3Configuration.builder()
                .pathStyleAccessEnabled(true)  // For Linode, path style access should be enabled
                .build();

        // Return the configured S3Client
        return S3Client.builder()
                .region(Region.AP_SOUTH_1)  // Use the region you are targeting
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .endpointOverride(URI.create(endpoint))  // Add the endpointOverride with the correct scheme
                .serviceConfiguration(s3Config)
                .build();
    }
}
