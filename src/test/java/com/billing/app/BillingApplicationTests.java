package com.billing.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BillingApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    void fail() {
        throw new RuntimeException("hi");
    }

}
