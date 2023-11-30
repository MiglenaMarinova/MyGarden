package com.example.mygarden.web;

import com.example.mygarden.service.CategoryService;
import com.example.mygarden.testUtil.TestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class CategoriesControllerTest {

    @Autowired
    private TestData testData;
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private CategoryService categoryService;


    @BeforeEach
    void configureSystemUnderTest(){

        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(new CategoriesController(categoryService))
                .build();

    }


    @AfterEach
    void tearDown() {
        testData.cleanAllTestData();
    }

    @Test
    void getVeggiesTest() throws Exception {
        this.mockMvc.perform(get("/categories/veggies"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allCat"))
                .andExpect(model().attributeExists("allVeggies"))
                .andExpect(view().name("categories"));
    }

    @Test
    void getOtherTest() throws Exception {
        this.mockMvc.perform(get("/categories/others"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allCat"))
                .andExpect(model().attributeExists("allOther"))
                .andExpect(view().name("categories"));
    }

    @Test
    void getSpicesTest() throws Exception {
        this.mockMvc.perform(get("/categories/spices"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allCat"))
                .andExpect(model().attributeExists("allSpices"))
                .andExpect(view().name("categories"));
    }

    @Test
    void getHomemadeTest() throws Exception {
        this.mockMvc.perform(get("/categories/homemades"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allCat"))
                .andExpect(model().attributeExists("allHomemade"))
                .andExpect(view().name("categories"));
    }
}