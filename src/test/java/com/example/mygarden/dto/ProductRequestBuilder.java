package com.example.mygarden.dto;

import com.example.mygarden.model.dto.ProductAddDto;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


public class ProductRequestBuilder {

    private final MockMvc mockMvc;

    public ProductRequestBuilder(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public ResultActions create(ProductAddDto testAddDto) throws Exception {

        return mockMvc.perform(MockMvcRequestBuilders.post("/products/add")
                .param("name", testAddDto.getName())
                .param("price", String.valueOf(testAddDto.getPrice()))
                .param("category", String.valueOf(testAddDto.getCategory())));
    }



}
