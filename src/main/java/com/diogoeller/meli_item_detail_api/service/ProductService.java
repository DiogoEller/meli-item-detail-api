package com.diogoeller.meli_item_detail_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.diogoeller.meli_item_detail_api.dto.ProductDto;
import com.diogoeller.meli_item_detail_api.mapper.ProductMapper;
import com.diogoeller.meli_item_detail_api.model.Product;
import com.diogoeller.meli_item_detail_api.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDto> getAllProducts() {
        log.info("Obtendo todos os produtos do repositório");
        List<Product> products = productRepository.findAll();
        log.debug("Total de produtos encontrados: {}", products.size());
        return ProductMapper.toDtoList(products);
    }

    public ProductDto getProductById(String id) {
        log.info("Buscando produto pelo id: {}", id);
        Product product = productRepository.findById(id);
        if (product == null) {
            log.warn("Produto com id '{}' não encontrado no repositório.", id);
        } else {
            log.debug("Produto encontrado: {}", product.getTitle());
        }
        return ProductMapper.toDto(product);
    }

    public ProductDto createProduct(ProductDto productDto) {
        log.info("Salvando novo produto: {}", productDto.getTitle());
        Product product = ProductMapper.toEntity(productDto);
        Product savedProduct = productRepository.save(product);
        log.debug("Produto salvo com id: {}", savedProduct.getId());
        return ProductMapper.toDto(savedProduct);
    }

    public ProductDto updateProduct(String id, ProductDto productDto) {
        log.info("Atualizando produto com id: {}", id);
        Product product = ProductMapper.toEntity(productDto);
        Product updatedProduct = productRepository.update(id, product);
        if (updatedProduct == null) {
            log.warn("Produto com id '{}' não encontrado para atualização.", id);
        } else {
            log.debug("Produto atualizado: {}", updatedProduct.getTitle());
        }
        return ProductMapper.toDto(updatedProduct);
    }

    public void deleteProduct(String id) {
        log.info("Removendo produto com id: {}", id);
        productRepository.delete(id);
        log.debug("Produto com id '{}' removido do repositório.", id);
    }
}