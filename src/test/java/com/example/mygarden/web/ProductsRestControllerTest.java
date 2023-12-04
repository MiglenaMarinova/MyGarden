package com.example.mygarden.web;

import com.example.mygarden.model.entity.Order;
import com.example.mygarden.model.entity.Picture;
import com.example.mygarden.model.entity.Product;
import com.example.mygarden.repository.OrderRepository;
import com.example.mygarden.repository.ProductRepository;
import com.example.mygarden.service.ProductService;
import com.example.mygarden.testUtil.TestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductsRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestData testData;
    @Autowired
    private ObjectMapper objectMapper;
    @Mock
    private ProductRepository productRepository;


    @BeforeEach
    void setUp() {
        testData.cleanAllTestData();
    }

    @AfterEach
    void tearDown() {
        testData.cleanAllTestData();
    }

    @Test
    public void testGetAll() throws Exception {
        Set<Picture> pictureSet = new HashSet<>();
        testData.createProduct(1L, "Name1", BigDecimal.valueOf(1.00), pictureSet);


        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].name", is("Name1")));

    }

    @Test
    public void testFindById() throws Exception {
        Set<Picture> pictureSet = new HashSet<>();

        Product test1 = testData.createProduct(1L, "Name1", BigDecimal.valueOf(1.00), pictureSet);
        long id = test1.getId();

        when(productRepository.findById(id)).thenReturn(Optional.of(test1));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/products/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(test1.getName()));

    }

    @Test
    void shouldReturnNotFound() throws Exception {
        long id = 3L;

        when(productRepository.findById(id)).thenReturn(Optional.empty());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/products/{id}", id))
                .andExpect(status().isNotFound());

    }

    @Test
    void testUpdateProduct() throws Exception {

        Set<Picture> pictureSet = new HashSet<>();

        Product test1 = testData.createProduct(2L, "Name1", BigDecimal.valueOf(1.00), pictureSet);
        Product updatedProduct = testData.createProduct(2L, "Name1", BigDecimal.valueOf(3.00), pictureSet);
        long id = test1.getId();
        when(productRepository.findById(id)).thenReturn(Optional.of(test1));
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedProduct.getName()))
                .andExpect(jsonPath("$.price").value(updatedProduct.getPrice()));

        System.out.println();
    }


}