package com.diogoeller.meli_item_detail_api.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.diogoeller.meli_item_detail_api.dto.ProductDto;
import com.diogoeller.meli_item_detail_api.interfaces.ProductServiceInterface;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductServiceInterface productService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ProductDto getSampleProduct() {
        ProductDto dto = new ProductDto();
        dto.setId("MLB123");
        dto.setTitle("Test Product");
        dto.setDescription("Description");
        dto.setPrice(BigDecimal.valueOf(100.0));
        dto.setCategory("Eletrônicos");
        dto.setSeller("Seller");
        dto.setAvailableQuantity(5);
        dto.setPictures(List.of("url1", "url2"));
        return dto;
    }

    @Test
    void testGetAllProductsSuccess() throws Exception {
        when(productService.getAllProducts()).thenReturn(Collections.singletonList(getSampleProduct()));

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("MLB123"));
    }

    @Test
    void testGetProductByIdSuccess() throws Exception {
        when(productService.getProductById("MLB123")).thenReturn(getSampleProduct());

        mockMvc.perform(get("/api/v1/products/MLB123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("MLB123"));
    }

    @Test
    void testGetProductByIdNotFound() throws Exception {
        when(productService.getProductById("notfound")).thenReturn(null);

        mockMvc.perform(get("/api/v1/products/notfound"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateProductSuccess() throws Exception {
        ProductDto dto = getSampleProduct();
        when(productService.createProduct(dto)).thenReturn(dto);

        mockMvc.perform(post("/api/v1/products")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("MLB123"));
    }

    @Test
    void testCreateProductError() throws Exception {
        ProductDto dto = getSampleProduct();
        when(productService.createProduct(dto)).thenReturn(null);

        mockMvc.perform(post("/api/v1/products")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateProductSuccess() throws Exception {
        ProductDto dto = getSampleProduct();
        when(productService.updateProduct("MLB123", dto)).thenReturn(dto);

        mockMvc.perform(put("/api/v1/products/MLB123")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("MLB123"));
    }

    @Test
    void testUpdateProductNotFound() throws Exception {
        ProductDto dto = getSampleProduct();
        when(productService.updateProduct("notfound", dto)).thenReturn(null);

        mockMvc.perform(put("/api/v1/products/notfound")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteProductSuccess() throws Exception {
        ProductDto dto = getSampleProduct();
        when(productService.getProductById("MLB123")).thenReturn(dto);

        mockMvc.perform(delete("/api/v1/products/MLB123"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteProductNotFound() throws Exception {
        when(productService.getProductById("notfound")).thenReturn(null);

        mockMvc.perform(delete("/api/v1/products/notfound"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetRelatedItemsSuccess() throws Exception {
        ProductDto dto = getSampleProduct();
        when(productService.getRelatedItems("Eletrônicos")).thenReturn(Arrays.asList(dto));

        mockMvc.perform(get("/api/v1/products/related")
                .param("category", "Eletrônicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value("Eletrônicos"));
    }

    @Test
    void testGetRelatedItemsNotFound() throws Exception {
        when(productService.getRelatedItems("CategoriaInexistente")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/products/related")
                .param("category", "CategoriaInexistente"))
                .andExpect(status().isNotFound());
    }
}