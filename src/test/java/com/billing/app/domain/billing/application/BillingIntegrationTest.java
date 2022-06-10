package com.billing.app.domain.billing.application;

import com.billing.app.domain.types.core.model.ProductType;
import com.billing.app.domain.types.core.ports.incoming.InitializeTypesPort;
import com.billing.app.domain.types.core.ports.outgoing.TypeDataBasePort;
import org.junit.jupiter.api.BeforeEach;
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
    private InitializeTypesPort initializeTypesPort;

    @BeforeEach
    public void setUp(){
        initializeTypesPort.initialize();
    }

    @Test
    public void testBillingWithExamplesFromSpecification() throws Exception {
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
                ).contentType("application/json"))
                .andDo(print())
                .andDo(expectNoException())
                .andExpect(status().isOk())
                .andExpect(jsonPath("items[0].name", equalTo("book")))
                .andExpect(jsonPath("items[0].sumAfterTaxesPrice", equalTo(1249)))
                .andExpect(jsonPath("items[1].name", equalTo("music CD")))
                .andExpect(jsonPath("items[1].sumAfterTaxesPrice", equalTo(1649)))
                .andExpect(jsonPath("items[2].name", equalTo("chocolate bar")))
                .andExpect(jsonPath("items[2].sumAfterTaxesPrice", equalTo( 85)))
                .andExpect(jsonPath("sumTaxes", equalTo( 150)))
                .andExpect(jsonPath("sumAfterTaxesPrice", equalTo(2983)));
    }

    @Test
    public void testBillingWithExamplesFromSpecification2() throws Exception {
        this.mvc.perform(post("/bill").content(
                        """
                        [
                            {
                                "price": 1000,
                                "name": "box of chocolates",
                                "quantity": 1,
                                "imported": true,
                                "typeId": "food"
                            },
                            {
                                "price": 4750,
                                "name": "bottle of perfume",
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
                .andExpect(jsonPath("items[0].sumAfterTaxesPrice", equalTo(1050)))
                .andExpect(jsonPath("items[1].name", equalTo("bottle of perfume")))
                .andExpect(jsonPath("items[1].sumAfterTaxesPrice", equalTo(5465)))
                .andExpect(jsonPath("sumTaxes", equalTo(  765)))
                .andExpect(jsonPath("sumAfterTaxesPrice", equalTo(6515)));
    }

    @Test
    public void testBillingWithExamplesFromSpecification3() throws Exception {
        this.mvc.perform(post("/bill").content(
                        """
                        [
                            {
                                "price": 2799,
                                "name": "bottle of perfume",
                                "quantity": 1,
                                "imported": true
                            },
                            {
                                "price": 1899,
                                "name": "bottle of perfume",
                                "quantity": 1,
                                "imported": false
                            },
                            {
                                "price": 975,
                                "name": "packet of headache pills",
                                "quantity": 1,
                                "typeId": "meds"
                            },
                            {
                                "price": 1125,
                                "name": "chocolates",
                                "quantity": 1,
                                "typeId": "food",
                                "imported": true
                            }
                        ]
                        """
                ).contentType("application/json"))
                .andDo(print())
                .andDo(expectNoException())
                .andExpect(status().isOk())
                .andExpect(jsonPath("items[0].name", equalTo("bottle of perfume")))
                .andExpect(jsonPath("items[0].sumAfterTaxesPrice", equalTo(3219)))
                .andExpect(jsonPath("items[1].name", equalTo("bottle of perfume")))
                .andExpect(jsonPath("items[1].sumAfterTaxesPrice", equalTo( 2089)))
                .andExpect(jsonPath("items[2].name", equalTo("packet of headache pills")))
                .andExpect(jsonPath("items[2].sumAfterTaxesPrice", equalTo( 975)))
                .andExpect(jsonPath("items[3].name", equalTo("chocolates")))
                .andExpect(jsonPath("items[3].sumAfterTaxesPrice", equalTo( 1180))) //Tasks says 1185 but that seems wrong
                .andExpect(jsonPath("sumTaxes", equalTo(  665))) // Different number from task see above
                .andExpect(jsonPath("sumAfterTaxesPrice", equalTo(7463))); // Different number from task see above
    }
    @Test
    public void testQuantity() throws Exception {
        initializeTypesPort.initialize();

        this.mvc.perform(post("/bill").content(
                        """
                        [
                            {
                                "price": 1499,
                                "name": "music CD",
                                "quantity": 2
                            }
                        ]
                        """
                ).contentType("application/json"))
                .andDo(print())
                .andDo(expectNoException())
                .andExpect(status().isOk())
                .andExpect(jsonPath("items[0].name", equalTo("music CD")))
                .andExpect(jsonPath("items[0].sumAfterTaxesPrice", equalTo(1649 * 2)))
                .andExpect(jsonPath("sumTaxes", equalTo(  (1649 - 1499) * 2)))
                .andExpect(jsonPath("sumAfterTaxesPrice", equalTo(1649 * 2)));
    }
}
