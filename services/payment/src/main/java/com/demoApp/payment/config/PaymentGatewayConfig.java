package com.demoApp.payment.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@RequiredArgsConstructor
@Slf4j
public class PaymentGatewayConfig {

    private final PaymentProperties paymentProperties;

    public String getApiKey() {
        log.debug("Fetching API key");
        return paymentProperties.getApiKey();
    }

    public String getSecret() {
        log.debug("Fetching secret");
        return paymentProperties.getSecret();
    }
}
