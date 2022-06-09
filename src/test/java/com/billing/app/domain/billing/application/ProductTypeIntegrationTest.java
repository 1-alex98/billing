package com.billing.app.domain.billing.application;

import com.billing.app.domain.types.core.model.ProductType;
import com.billing.app.domain.types.core.ports.outgoing.TypeDataBasePort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static com.billing.app.TestUtils.expectNoException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductTypeIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private TypeDataBasePort typeDataBasePort;

    @Test
    public void testCreateType() throws Exception {
        this.mvc.perform(post("/types").content(
                        """
                        {
                            "id":"wood",
                            "name":"Wood"
                        }      
                        """
                ).contentType("application/json"))
                .andDo(print())
                .andDo(expectNoException())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", equalTo("Wood")))
                .andExpect(jsonPath("id", equalTo("wood")));

        assertThat(typeDataBasePort.findAll())
                .hasSize(1)
                .element(0)
                .extracting(ProductType::getId)
                .isEqualTo("wood");
    }

    @Test
    public void testFetchTypes() throws Exception {
        typeDataBasePort.save(new ProductType(
                "id",
                "name",
                "des",
                0.10,
                0.05
        ));

        this.mvc.perform(get("/types"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(expectNoException())
                .andExpect(jsonPath("[0].name", equalTo("name")))
                .andExpect(jsonPath("[0].id", equalTo("id")))
                .andExpect(jsonPath("[0].description", equalTo("des")));
    }
}
