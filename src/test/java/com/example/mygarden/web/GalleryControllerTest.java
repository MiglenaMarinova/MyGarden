package com.example.mygarden.web;

import com.example.mygarden.service.CategoryService;
import com.example.mygarden.service.PictureService;
import com.example.mygarden.testUtil.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class GalleryControllerTest {

    @Autowired
    private TestData testData;
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private PictureService pictureService;

    @Test
    void allUploadedPicTest() throws Exception {
        this.mockMvc.perform(get("/pictures/all"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pictures"))
                .andExpect(view().name("gallery"));
    }
}