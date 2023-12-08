package com.example.mygarden.web;

import com.example.mygarden.dto.ProductRequestBuilder;
import com.example.mygarden.model.dto.ProductAddDto;
import com.example.mygarden.service.ProductService;
import com.example.mygarden.service.ShoppingBasketService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ProductAddControllerTest {


    private ProductRequestBuilder requestBuilder;
    private ProductService productService;
    private  ShoppingBasketService shoppingBasketService;


    @BeforeEach
    void configureSystemUnderTest() {
        productService = mock(ProductService.class);

        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(new ProductController(productService, shoppingBasketService))
                .build();
        requestBuilder = new ProductRequestBuilder(mockMvc);
    }

    @Nested
    @DisplayName("Submit the  add Dto")
    class SubmitProductAddDto {

        private ProductAddDto testAddDto;


        @Nested
        @DisplayName("When validation fails")
        class WhenValidationFails {

            @BeforeEach
            void createProductAddDto() {
                testAddDto = new ProductAddDto();
                testAddDto.setName(null);

            }

            @Test
            @DisplayName("Should redirect to add")
            void shouldRedirectToAdd() throws Exception {
                requestBuilder.create(testAddDto)
                        .andExpect(view().name("redirect:add"));
            }

            @Test
            @DisplayName("should display validation error about null name")
            void shouldDisplayValidationErrorNullName() throws Exception {
                requestBuilder.create(testAddDto);
                Assertions.assertNull(testAddDto.getName(), "Name must be not null");

            }


        }


    }
}

