package com.demoApp.delivery;

import com.demoApp.delivery.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.context.annotation.Import;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(properties = {
    "spring.kafka.enabled=false",
    "spring.cloud.config.enabled=false",
    "eureka.client.enabled=false"
})
@Import(TestConfig.class)
class DeliveryApplicationTests {

	@Test
	void contextLoads() {
	}

}
