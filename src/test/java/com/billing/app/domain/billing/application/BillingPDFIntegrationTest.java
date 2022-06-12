package com.billing.app.domain.billing.application;

import com.billing.app.domain.types.core.ports.incoming.InitializeTypesPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static com.billing.app.TestUtils.expectNoException;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BillingPDFIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private InitializeTypesPort initializeTypesPort;

    @BeforeEach
    public void setUp() {
        initializeTypesPort.initialize();
    }

    @Test
    public void testBillingWithExamplesFromSpecificationWithPDFAsOutput() throws Exception {
        this.mvc.perform(post("/bill").content(
                        """
                                [
                                    {
                                        "price": 1249,
                                        "name": "book",
                                        "quantity": 1,
                                        "imported": false,
                                        "typeId": "books"
                                    },
                                    {
                                        "price": 1499,
                                        "name": "music CD",
                                        "quantity": 1,
                                        "imported": false
                                    },
                                    {
                                        "price": 85,
                                        "name": "chocolate bar",
                                        "quantity": 1,
                                        "imported": false,
                                        "typeId": "food"
                                    }
                                ]
                                """
                ).contentType("application/json").accept("application/pdf"))
                .andDo(print())
                .andDo(expectNoException())
                .andExpect(status().isOk());

    }
}
