package com.example.mygarden.web;

import com.example.mygarden.dto.ProductRequestBuilder;
import com.example.mygarden.model.dto.ProductAddDto;
import com.example.mygarden.service.ProductService;
import org.junit.jupiter.api.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ProductAddControllerTest {


    private ProductRequestBuilder requestBuilder;
    private ProductService productService;


    @BeforeEach
    void configureSystemUnderTest(){
        productService = mock(ProductService.class);

        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(new ProductController(productService))
                .build();
        requestBuilder = new ProductRequestBuilder(mockMvc);
    }
    @Nested
    @DisplayName("Submit the  add Dto")
    class SubmitProductAddDto{

        private static final int MIN_LENGTH_NAME = 3;
        private static final int MAX_LENGTH_NAME = 20;
        private static final int MIN_PRICE = 0;
        public static final String FORM_OBJECT = "product";

        private ProductAddDto testAddDto;
        @Nested
        @DisplayName("When validation fails")
        class  WhenValidationFails{
            private static final String FROM_FIELD_NAME_NAME = "name";
            private static final String FORM_FIELD_NAME_PRICE = "price";

            private static final String VALIDATION_ERROR_NOT_NULL = "NotNull";

            @BeforeEach
            void createProductAddDto(){
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


//            @Test
//            @DisplayName("should display one validation error")
//            void shouldDisplayOneValidationError() throws Exception {
//                requestBuilder.create(testAddDto)
//                        .andExpect(model().attributeErrorCount(FORM_OBJECT, 1));
//            }

//            @Test
//            @DisplayName("should display validation error about null name")
//            void shouldDisplayValidationErrorNullName() throws Exception {
//                requestBuilder.create(testAddDto)
//                        .andExpect(model().attributeHasFieldErrorCode(
//                                FORM_OBJECT,
//                                FROM_FIELD_NAME_NAME,
//                                VALIDATION_ERROR_NOT_NULL
//                        ));
//            }


        }
        @Nested
        @DisplayName("When validation is successful")
        class  WhenValidationIsSuccessful{

        }
    }
}
