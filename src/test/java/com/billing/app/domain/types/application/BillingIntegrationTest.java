package com.billing.app.domain.types.application;

import com.billing.app.domain.types.core.model.ProductType;
import com.billing.app.domain.types.core.ports.incoming.InitializeTypesPort;
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
public class BillingIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private TypeDataBasePort typeDataBasePort;
    @Autowired
    private InitializeTypesPort initializeTypesPort;

    @Test
    public void testBillingWithExamplesFromSpecification() throws Exception {
        initializeTypesPort.initialize();

        this.mvc.perform(post("/bill").content(
                        """
                        [
                            {
                                "price": 12.49,
                                "name": "book",
                                "quantity": 1,
                                "imported": false,
                                "typeId": "book"
                            },
                            {
                                "price": 14.99,
                                "name": "music CD",
                                "quantity": 1,
                                "imported": false
                            },
                            {
                                "price": 0.85,
                                "name": "chocolate bar",
                                "quantity": 1,
                                "imported": false,
                                "typeId": "food"
                            }
                        ]
                        """
                ).contentType("application/json"))
                .andDo(print())
                .andDo(expectNoException())
                .andExpect(status().isOk())
                .andExpect(jsonPath("items[0].name", equalTo("book")))
                .andExpect(jsonPath("items[0].sumAfterTaxesPrice", equalTo(12.49)))
                .andExpect(jsonPath("items[1].name", equalTo("music CD")))
                .andExpect(jsonPath("items[1].sumAfterTaxesPrice", equalTo(16.49)))
                .andExpect(jsonPath("items[2].name", equalTo("chocolate bar")))
                .andExpect(jsonPath("items[2].sumAfterTaxesPrice", equalTo( 0.85)))
                .andExpect(jsonPath("sumTaxes", equalTo( 1.50)))
                .andExpect(jsonPath("sumAfterTaxesPrice", equalTo(29.83)));
    }

    @Test
    public void testBillingWithExamplesFromSpecification2() throws Exception {
        initializeTypesPort.initialize();

        this.mvc.perform(post("/bill").content(
                        """
                        [
                            {
                                "price": 10.00,
                                "name": "box of chocolates",
                                "quantity": 1,
                                "imported": true,
                                "typeId": "food"
                            },
                            {
                                "price": 47.50,
                                "name": " bottle of perfume",
                                "quantity": 1,
                                "imported": true
                            }
                        ]
                        """
                ).contentType("application/json"))
                .andDo(print())
                .andDo(expectNoException())
                .andExpect(status().isOk())
                .andExpect(jsonPath("items[0].name", equalTo("box of chocolates")))
                .andExpect(jsonPath("items[0].sumAfterTaxesPrice", equalTo(10.50)))
                .andExpect(jsonPath("items[1].name", equalTo("bottle of perfume")))
                .andExpect(jsonPath("items[1].sumAfterTaxesPrice", equalTo(54.65)))
                .andExpect(jsonPath("sumTaxes", equalTo(  7.65)))
                .andExpect(jsonPath("sumAfterTaxesPrice", equalTo(65.15)));
    }

    @Test
    public void testBillingWithExamplesFromSpecification3() throws Exception {
        initializeTypesPort.initialize();

        this.mvc.perform(post("/bill").content(
                        """
                        [
                            {
                                "price": 27.99,
                                "name": "bottle of perfume",
                                "quantity": 1,
                                "imported": true
                            },
                            {
                                "price": 18.99,
                                "name": " bottle of perfume",
                                "quantity": 1,
                                "imported": true
                            },
                            {
                                "price": 18.99,
                                "name": "packet of headache pills",
                                "quantity": 1,
                                "typeId": "meds"
                            },
                            {
                                "price": 11.25,
                                "name": "chocolates",
                                "quantity": 1,
                                "typeId": "food"
                            }
                        ]
                        """
                ).contentType("application/json"))
                .andDo(print())
                .andDo(expectNoException())
                .andExpect(status().isOk())
                .andExpect(jsonPath("items[0].name", equalTo("bottle of perfume")))
                .andExpect(jsonPath("items[0].sumAfterTaxesPrice", equalTo(32.19)))
                .andExpect(jsonPath("items[1].name", equalTo("bottle of perfume")))
                .andExpect(jsonPath("items[1].sumAfterTaxesPrice", equalTo( 20.89)))
                .andExpect(jsonPath("items[2].name", equalTo("packet of headache pills")))
                .andExpect(jsonPath("items[2].sumAfterTaxesPrice", equalTo( 9.75)))
                .andExpect(jsonPath("items[3].name", equalTo("chocolates")))
                .andExpect(jsonPath("items[3].sumAfterTaxesPrice", equalTo( 11.85)))
                .andExpect(jsonPath("sumTaxes", equalTo(  6.70)))
                .andExpect(jsonPath("sumAfterTaxesPrice", equalTo(74.68)));
    }
    @Test
    public void testQuantity() throws Exception {
        initializeTypesPort.initialize();

        this.mvc.perform(post("/bill").content(
                        """
                        [
                            {
                                "price": 14.99,
                                "name": "music CD",
                                "quantity": 1
                            }
                        ]
                        """
                ).contentType("application/json"))
                .andDo(print())
                .andDo(expectNoException())
                .andExpect(status().isOk())
                .andExpect(jsonPath("items[0].name", equalTo("music CD")))
                .andExpect(jsonPath("items[0].sumAfterTaxesPrice", equalTo(16.49 * 2)))
                .andExpect(jsonPath("sumTaxes", equalTo(  (16.49 - 14.99) * 2)))
                .andExpect(jsonPath("sumAfterTaxesPrice", equalTo(16.49 * 2)));
    }
}
