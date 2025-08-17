package com.diogoeller.meli_item_detail_api;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.diogoeller.meli_item_detail_api.controller.ProductController;
import com.diogoeller.meli_item_detail_api.repository.ProductRepository;
import com.diogoeller.meli_item_detail_api.service.ProductService;

@SpringBootTest
class MeliItemDetailApiApplicationTests {

    @Autowired
    private ProductController productController;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void contextLoads() {
        // Testa se o contexto Spring Boot carrega
    }

    @Test
    void productControllerBeanExists() {
        assertNotNull(productController, "ProductController deve estar presente no contexto");
    }

    @Test
    void productServiceBeanExists() {
        assertNotNull(productService, "ProductService deve estar presente no contexto");
    }

    @Test
    void productRepositoryBeanExists() {
        assertNotNull(productRepository, "ProductRepository deve estar presente no contexto");
    }

}
