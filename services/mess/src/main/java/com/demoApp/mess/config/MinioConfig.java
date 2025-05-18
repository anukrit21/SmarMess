package com.demoApp.mess.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Configuration
@ConditionalOnProperty(name = "app.minio.enabled", havingValue = "true")
public class MinioConfig {
    
    @Value("${app.minio.endpoint}")
    private String endpoint;
    
    @Value("${app.minio.access-key}")
    private String accessKey;
    
    @Value("${app.minio.secret-key}")
    private String secretKey;
    
    @Value("${app.minio.bucket}")
    private String bucket;
    
    @Value("${app.minio.region}")
    private String region;
    
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .region(region)
                .build();
    }
} 