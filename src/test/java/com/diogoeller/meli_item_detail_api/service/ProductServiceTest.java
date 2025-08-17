package com.diogoeller.meli_item_detail_api.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.diogoeller.meli_item_detail_api.dto.ProductDto;
import com.diogoeller.meli_item_detail_api.interfaces.ProductRepositoryInterface;
import com.diogoeller.meli_item_detail_api.model.Product;

class ProductServiceTest {

    private ProductDto getSampleProductDto() {
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

    private Product getSampleProduct() {
        Product product = new Product();
        product.setId("MLB123");
        product.setTitle("Test Product");
        product.setDescription("Description");
        product.setPrice(BigDecimal.valueOf(100.0));
        product.setCategory("Eletrônicos");
        product.setSeller("Seller");
        product.setAvailableQuantity(5);
        product.setPictures(List.of("url1", "url2"));
        return product;
    }

    @Test
    void testGetAllProductsReturnsList() {
        ProductRepositoryInterface repo = mock(ProductRepositoryInterface.class);
        ProductService service = new ProductService(repo);

        Product product = getSampleProduct();
        when(repo.findAll()).thenReturn(Collections.singletonList(product));

        List<ProductDto> result = service.getAllProducts();
        assertEquals(1, result.size());
        assertEquals("MLB123", result.get(0).getId());
    }

    @Test
    void testGetProductByIdFound() {
        ProductRepositoryInterface repo = mock(ProductRepositoryInterface.class);
        ProductService service = new ProductService(repo);

        Product product = getSampleProduct();
        when(repo.findById("MLB123")).thenReturn(product);

        ProductDto result = service.getProductById("MLB123");
        assertNotNull(result);
        assertEquals("MLB123", result.getId());
    }

    @Test
    void testGetProductByIdNotFound() {
        ProductRepositoryInterface repo = mock(ProductRepositoryInterface.class);
        ProductService service = new ProductService(repo);

        when(repo.findById("notfound")).thenReturn(null);

        ProductDto result = service.getProductById("notfound");
        assertNull(result);
    }

    @Test
    void testCreateProductSuccess() {
        ProductRepositoryInterface repo = mock(ProductRepositoryInterface.class);
        ProductService service = new ProductService(repo);

        ProductDto dto = getSampleProductDto();
        Product product = getSampleProduct();
        when(repo.save(any(Product.class))).thenReturn(product);

        ProductDto result = service.createProduct(dto);
        assertNotNull(result);
        assertEquals("MLB123", result.getId());
    }

    @Test
    void testUpdateProductSuccess() {
        ProductRepositoryInterface repo = mock(ProductRepositoryInterface.class);
        ProductService service = new ProductService(repo);

        ProductDto dto = getSampleProductDto();
        Product product = getSampleProduct();
        when(repo.update(eq("MLB123"), any(Product.class))).thenReturn(product);

        ProductDto result = service.updateProduct("MLB123", dto);
        assertNotNull(result);
        assertEquals("MLB123", result.getId());
    }

    @Test
    void testUpdateProductNotFound() {
        ProductRepositoryInterface repo = mock(ProductRepositoryInterface.class);
        ProductService service = new ProductService(repo);

        ProductDto dto = getSampleProductDto();
        when(repo.update(eq("notfound"), any(Product.class))).thenReturn(null);

        ProductDto result = service.updateProduct("notfound", dto);
        assertNull(result);
    }

    @Test
    void testDeleteProduct() {
        ProductRepositoryInterface repo = mock(ProductRepositoryInterface.class);
        ProductService service = new ProductService(repo);

        doNothing().when(repo).delete("MLB123");
        assertDoesNotThrow(() -> service.deleteProduct("MLB123"));
        verify(repo, times(1)).delete("MLB123");
    }

    @Test
    void testGetRelatedItemsReturnsList() {
        ProductRepositoryInterface repo = mock(ProductRepositoryInterface.class);
        ProductService service = new ProductService(repo);

        Product product = getSampleProduct();
        when(repo.findByCategory("Eletrônicos")).thenReturn(Collections.singletonList(product));

        List<ProductDto> result = service.getRelatedItems("Eletrônicos");
        assertEquals(1, result.size());
        assertEquals("Eletrônicos", result.get(0).getCategory());
    }

    @Test
    void testGetRelatedItemsReturnsEmptyList() {
        ProductRepositoryInterface repo = mock(ProductRepositoryInterface.class);
        ProductService service = new ProductService(repo);

        when(repo.findByCategory("CategoriaInexistente")).thenReturn(Collections.emptyList());

        List<ProductDto> result = service.getRelatedItems("CategoriaInexistente");
        assertTrue(result.isEmpty());
    }
}
