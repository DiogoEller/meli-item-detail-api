package com.diogoeller.meli_item_detail_api.service;

import com.diogoeller.meli_item_detail_api.dto.ProductDto;
import com.diogoeller.meli_item_detail_api.mapper.ProductMapper;
import com.diogoeller.meli_item_detail_api.model.Product;
import com.diogoeller.meli_item_detail_api.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ProductMapper.toDtoList(products);
    }

    public ProductDto getProductById(String id) {
        Product product = productRepository.findById(id);
        return ProductMapper.toDto(product);
    }

    public ProductDto createProduct(ProductDto productDto) {
        Product product = ProductMapper.toEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDto(savedProduct);
    }

    public ProductDto updateProduct(String id, ProductDto productDto) {
        Product product = ProductMapper.toEntity(productDto);
        Product updatedProduct = productRepository.update(id, product);
        return ProductMapper.toDto(updatedProduct);
    }

    public void deleteProduct(String id) {
        productRepository.delete(id);
    }
}