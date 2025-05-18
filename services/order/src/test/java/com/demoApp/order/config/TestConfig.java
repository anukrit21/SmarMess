package com.demoApp.order.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.mockito.Mockito;

@TestConfiguration
@EnableWebSecurity
@EnableDiscoveryClient
@EnableFeignClients
public class TestConfig {

    @MockBean
    private DiscoveryClient discoveryClient;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll());
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        JwtDecoder jwtDecoder = Mockito.mock(JwtDecoder.class);
        Mockito.when(jwtDecoder.decode(Mockito.anyString())).thenReturn(
            Jwt.withTokenValue("mock-token")
                .header("alg", "none")
                .claim("sub", "test-user")
                .build()
        );
        return jwtDecoder;
    }
} 