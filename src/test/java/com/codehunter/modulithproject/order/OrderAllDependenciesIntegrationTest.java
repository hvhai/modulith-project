package com.codehunter.modulithproject.order;

import com.codehunter.modulithproject.TestSecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.test.context.ActiveProfiles;

@ApplicationModuleTest(ApplicationModuleTest.BootstrapMode.ALL_DEPENDENCIES)
@ActiveProfiles("integration")
@Import(TestSecurityConfiguration.class)
class OrderAllDependenciesIntegrationTest {

    @Test
    void contextLoads() {
    }
}
