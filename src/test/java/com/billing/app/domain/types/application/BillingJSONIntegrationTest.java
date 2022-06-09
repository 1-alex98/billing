package com.billing.app.domain.types.application;

import com.billing.app.domain.types.core.model.ProductType;
import com.billing.app.domain.types.core.ports.outgoing.TypeDataBasePort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static com.billing.app.TestUtils.expectNoException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BillingJSONIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private TypeDataBasePort typeDataBasePort;

    @Test
    public void testBilling() throws Exception {
        typeDataBasePort.save(new ProductType(
                "food",
                "Food",
                "can be eaten",
                0,
                0.05
        ));

        this.mvc.perform(post("/types").content(
                        """
                        [
                        {
                            "price": 4.5,
                            "quantity": 3,
                            "imported": false
                        },
                        {
                            "price": 5.5,
                            "quantity": 2,
                            "imported": true,
                            "typeId": "food"
                        }
                        ]
                        """
                ).contentType("application/json"))
                .andDo(print())
                .andDo(expectNoException())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", equalTo("Wood")))
                .andExpect(jsonPath("id", equalTo("wood")));
    }
}
