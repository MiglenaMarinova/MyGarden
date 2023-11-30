package com.example.mygarden.web;

import com.example.mygarden.dto.ProductRequestBuilder;
import com.example.mygarden.model.dto.ProductAddDto;
import com.example.mygarden.model.entity.Category;
import com.example.mygarden.model.entity.Picture;
import com.example.mygarden.model.entity.Product;
import com.example.mygarden.model.enums.CategoryEnum;
import com.example.mygarden.repository.PictureRepository;
import com.example.mygarden.repository.ProductRepository;
import com.example.mygarden.service.PictureService;
import com.example.mygarden.service.ProductService;
import com.example.mygarden.testUtil.TestData;
import com.example.mygarden.testUtil.UserTestData;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.lang.Nullable;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class ProductControllerTest {


    public static final String USER_EMAIL = "user@test.com";
    public static final String MODERATOR_EMAIL = "moderator@test.com";
    public static final String ADMIN_EMAIL = "admin@test.com";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserTestData userTestData;
    @Autowired
    private TestData testData;


    @Mock
    private ProductRepository productRepository;
    @Mock
    private PictureRepository pictureRepositoryMock;
    @Mock
    private ProductService productServiceMock;
    @Mock
    private PictureService pictureServiceMock;

    private ProductAddDto productAddDto;
    private Product product;
    private Validator validator;

    @BeforeEach
    void configureSystemUnderTest(){

        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(new ProductController(productServiceMock))
                .build();

    }


    @AfterEach
    void tearDown() {
        userTestData.cleanAllTestData();
        testData.cleanAllTestData();
    }

    @Test
    @WithMockUser(username = ADMIN_EMAIL, roles = {"ADMIN"})
    void shouldReturnProductAddPage() throws Exception {
        userTestData.createTestAdmin(ADMIN_EMAIL);
        this.mockMvc.perform(get("/products/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("product-add"));
    }



    @Test
    @WithMockUser(username = ADMIN_EMAIL, roles = {"ADMIN"})
    void testProductAdd() throws Exception {
        userTestData.createTestAdmin(ADMIN_EMAIL);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/products/add")
                                .param("name", "Test" )
                                .param("price", String.valueOf(BigDecimal.valueOf(2.00)))
                                .param("category", String.valueOf(CategoryEnum.OTHER))

                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/products/add"));
    }
    @Test
    @WithMockUser(username = ADMIN_EMAIL, roles = {"ADMIN"})
    void testAddInvalidProduct() throws Exception {
        userTestData.createTestAdmin(ADMIN_EMAIL);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/products/add")
                                .param("name", "Te" )
                                .param("price", String.valueOf(BigDecimal.valueOf(2.00)))
                                .param("category", String.valueOf(CategoryEnum.OTHER))

                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:add"));
    }





    @Test
    @WithMockUser(username = MODERATOR_EMAIL, roles = {"MODERATOR"})
    void testChangeProductPic() throws Exception {
        userTestData.createTestAdmin(MODERATOR_EMAIL);

        Product testProduct = testData.createProduct(1L, "Name", BigDecimal.valueOf(2.00), new HashSet<>());
        long id = 1L;

        when(productRepository.findById(id)).thenReturn(Optional.of(testProduct));
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/products/change-pic/{id}", id))
//                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/moderator/manage"));

        Assertions.assertEquals(testProduct.getId(), id);

    }

    @Test
    @WithMockUser(username = USER_EMAIL, roles = {"USER"})
    void shouldDeleteProduct() throws Exception {
        userTestData.createTestUser(USER_EMAIL);
        long id = 1L;

        doNothing().when(productRepository).deleteById(id);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/products/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/products/all"));

        when(productRepository.findById(id)).thenReturn(Optional.empty());
    }







}