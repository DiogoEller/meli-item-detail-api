package com.diogoeller.meli_item_detail_api.interfaces;

import java.util.List;

import com.diogoeller.meli_item_detail_api.dto.ProductDto;

public interface ProductServiceInterface {
    List<ProductDto> getAllProducts();
    ProductDto getProductById(String id);
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(String id, ProductDto productDto);
    void deleteProduct(String id);
    List<ProductDto> getRelatedItems(String category);
}