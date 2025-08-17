package com.diogoeller.meli_item_detail_api.interfaces;

import java.util.List;

import com.diogoeller.meli_item_detail_api.model.Product;

public interface ProductRepositoryInterface {
    List<Product> findAll();
    Product findById(String id);
    Product save(Product product);
    Product update(String id, Product updatedProduct);
    void delete(String id);
    void saveAll(List<Product> products);
    List<Product> findByCategory(String category);
}