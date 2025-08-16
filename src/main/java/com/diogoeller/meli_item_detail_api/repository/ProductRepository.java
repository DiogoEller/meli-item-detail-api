package com.diogoeller.meli_item_detail_api.repository;

import com.diogoeller.meli_item_detail_api.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    private static final String FILE_PATH = "src/main/resources/data/products.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Product> findAll() {
        try {
            return objectMapper.readValue(new File(FILE_PATH), new TypeReference<List<Product>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Product findById(String id) {
        return findAll().stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Product save(Product product) {
        List<Product> products = findAll();
        products.add(product);
        saveAll(products);
        return product;
    }

    public Product update(String id, Product updatedProduct) {
        List<Product> products = findAll();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                updatedProduct.setId(id); // Garante que o id não seja alterado
                products.set(i, updatedProduct);
                saveAll(products);
                return updatedProduct;
            }
        }
        return null;
    }

    public void delete(String id) {
        List<Product> products = findAll();
        products.removeIf(product -> product.getId().equals(id));
        saveAll(products);
    }

    public void saveAll(List<Product> products) {
        try {
            objectMapper.writeValue(new File(FILE_PATH), products);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}