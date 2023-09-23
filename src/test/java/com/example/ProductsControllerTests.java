package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;


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
        products.setId(1);
        String requestBody = objectMapper.writeValueAsString(products);

        Mockito.when(service.add(products)).thenReturn(products);
        mockMvc.perform(MockMvcRequestBuilders.post(END_POINT_PATH)
                        .contentType("application/json").content(requestBody))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location" , ("/api/v1/products/1")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetByIdShouldReturn404NotFound() throws Exception{
        Integer searchId = 123 ;
        String requestURI = END_POINT_PATH + "/" + searchId ;

        Mockito.when(service.get(searchId)).thenThrow(UserNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get(requestURI).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void testGetByIdShouldReturn200Ok() throws Exception{
        Products products = new Products(1, "iPhone Xr" , 899);
        Integer searchId = 1 ;
        Mockito.when(service.get(products.getId())).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH + "/" + searchId).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetAllProductsShouldReturn200Ok () throws Exception {
        List<Products> products = new ArrayList<>();
        products.add(new Products(1,"iPhone Xr" , 899));
        products.add(new Products(2,"iPhone Xs Max" , 1099));
        products.add(new Products(3,"iPhone 11 Pro" , 1199));

        Mockito.when(service.getAllProducts()).thenReturn(products);
        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void testGetAllProductsShouldReturn204NoContent () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNoContent()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testUpdateProductShouldReturn404NotFound () throws Exception{
        Products existProducts = new Products(1 ,"Samsung A50" , 499);
        Integer searchId = 2 ;
        Products products = new Products("Samsung A50s" , 499);
        String requestBody = objectMapper.writeValueAsString(products);
        String requestBodyExist = objectMapper.writeValueAsString(existProducts);

        Mockito.when(service.add(existProducts)).thenReturn(existProducts);
        Mockito.when(service.update(products , searchId)).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.post(END_POINT_PATH).contentType("application/json").content(requestBodyExist));
        mockMvc.perform(MockMvcRequestBuilders.put(END_POINT_PATH +"/"+ searchId).contentType("application/json").content(requestBody))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void testUpdateProductShouldReturn200Ok () throws Exception{
        Products existProducts = new Products(1 ,"Samsung A50" , 499);
        Integer searchId = 1 ;
        Products products = new Products("Samsung A50s" , 499);
        String requestBody = objectMapper.writeValueAsString(products);
        String requestBodyExist = objectMapper.writeValueAsString(existProducts);

        Mockito.when(service.add(existProducts)).thenReturn(existProducts);
        Mockito.when(service.update(products , searchId)).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.post(END_POINT_PATH).contentType("application/json").content(requestBodyExist));
        mockMvc.perform(MockMvcRequestBuilders.put(END_POINT_PATH +"/"+ searchId).contentType("application/json").content(requestBody))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDeleteProductShouldReturn204NoContent() throws Exception{
        Products existProducts = new Products(1 ,"Samsung A50" , 499);

        Integer destinationId = 1 ;

        String requestBodyExist = objectMapper.writeValueAsString(existProducts);

        Mockito.when(service.add(existProducts)).thenReturn(existProducts);
        Mockito.when(service.delete(destinationId)).thenReturn(existProducts);
        mockMvc.perform(MockMvcRequestBuilders.post(END_POINT_PATH).contentType("application/json").content(requestBodyExist));
        mockMvc.perform(MockMvcRequestBuilders.delete(END_POINT_PATH + "/" +destinationId)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void testDeleteProductShouldReturn404NotFound() throws Exception{
        Products existProducts = new Products(1 ,"Samsung A50" , 499);

        Integer destinationId = 2 ;

        String requestBodyExist = objectMapper.writeValueAsString(existProducts);

        Mockito.when(service.add(existProducts)).thenReturn(existProducts);
        Mockito.when(service.delete(destinationId)).thenThrow(UserNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.post(END_POINT_PATH).contentType("application/json").content(requestBodyExist));
        mockMvc.perform(MockMvcRequestBuilders.delete(END_POINT_PATH + "/" +destinationId)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }
}
