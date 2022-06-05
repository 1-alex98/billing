package com.billing.app.billing.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static com.billing.app.TestUtils.expectNoException;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductTypeIntegrationTest {
    @Autowired
    private MockMvc mvc;

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
    }

    @Test
    public void testFetchTypes() throws Exception {
        this.mvc.perform(get("/types"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(expectNoException())
                .andExpect(jsonPath("[0].name", equalTo("test")))
                .andExpect(jsonPath("[0].id", equalTo("test")));
    }
}
