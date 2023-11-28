package com.example.mygarden.web;

import com.example.mygarden.model.entity.Picture;
import com.example.mygarden.model.entity.Product;
import com.example.mygarden.repository.PictureRepository;
import com.example.mygarden.repository.ProductRepository;
import com.example.mygarden.service.PictureService;
import com.example.mygarden.testUtil.TestData;
import com.example.mygarden.testUtil.UserTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
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
    private PictureRepository pictureRepository;
    @Mock
    private PictureService pictureService;




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
    @WithMockUser(username = MODERATOR_EMAIL, roles = {"MODERATOR"})
    void testChangeProductPic() throws Exception {
        userTestData.createTestAdmin(MODERATOR_EMAIL);

        Product testProduct = testData.createProduct(1L, "Name", BigDecimal.valueOf(2.00), new HashSet<>());
        long id = 1L;

        when(productRepository.findById(id)).thenReturn(Optional.of(testProduct));
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/products/change-pic/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/moderator/manage"));

        Assertions.assertEquals(testProduct.getId(), id);

    }


}