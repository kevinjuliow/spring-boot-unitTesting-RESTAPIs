package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(ProductsController.class)
public class ProductsControllerTests {
    private static final String END_POINT_PATH = "/api/v1/products";
    @Autowired
    private MockMvc mockMvc ;
    @Autowired
    private ObjectMapper objectMapper ;
    @MockBean
    private ProductService service ;

    @Test
    public void testAddShouldReturn400BadRequest () throws Exception {
        Products products = new Products("" , -1);

        String requestBody = objectMapper.writeValueAsString(products);

        mockMvc.perform(MockMvcRequestBuilders.post(END_POINT_PATH)
                        .contentType("application/json").content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testAddShouldReturn201Created () throws Exception {
        Products products = new Products("iPhone Xs Max" , 1299);

        String requestBody = objectMapper.writeValueAsString(products);

        mockMvc.perform(MockMvcRequestBuilders.post(END_POINT_PATH)
                        .contentType("application/json").content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location" , ("api/v1/products/1")))
                .andDo(MockMvcResultHandlers.print());
    }
}
