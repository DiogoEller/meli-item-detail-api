package com.diogoeller.meli_item_detail_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diogoeller.meli_item_detail_api.dto.ProductDto;
import com.diogoeller.meli_item_detail_api.exception.ResourceNotFoundException;
import com.diogoeller.meli_item_detail_api.interfaces.ProductServiceInterface;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductServiceInterface productService;

    @GetMapping
    @Operation(summary = "Lista todos os produtos", description = "Retorna uma lista de todos os produtos cadastrados")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        log.info("Buscando todos os produtos");
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca produto por ID", description = "Retorna o produto correspondente ao ID informado")
    public ResponseEntity<ProductDto> getProductById(
            @Parameter(description = "ID do produto", example = "MLB123") @PathVariable String id) {
        log.info("Buscando produto por id: {}", id);
        ProductDto product = productService.getProductById(id);
        if (product == null) {
            log.warn("Produto não encontrado: {}", id);
            throw new ResourceNotFoundException("Produto não encontrado: " + id);
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    @Operation(summary = "Cria um novo produto", description = "Adiciona um novo produto ao sistema")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        log.info("Criando produto: {}", productDto.getTitle());
        ProductDto createdProduct = productService.createProduct(productDto);
        if (createdProduct == null) {
            log.error("Erro ao criar produto: {}", productDto.getTitle());
            throw new ResourceNotFoundException("Erro ao criar produto.");
        }
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um produto", description = "Atualiza os dados de um produto existente")
    public ResponseEntity<ProductDto> updateProduct(
            @Parameter(description = "ID do produto", example = "MLB123") @PathVariable String id,
            @RequestBody ProductDto productDto) {
        log.info("Atualizando produto id: {}", id);
        ProductDto updatedProduct = productService.updateProduct(id, productDto);
        if (updatedProduct == null) {
            log.warn("Produto não encontrado para atualização: {}", id);
            throw new ResourceNotFoundException("Produto não encontrado para atualização: " + id);
        }
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um produto", description = "Remove um produto do sistema pelo ID")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID do produto", example = "MLB123") @PathVariable String id) {
        log.info("Excluindo produto id: {}", id);
        ProductDto product = productService.getProductById(id);
        if (product == null) {
            log.warn("Produto não encontrado para exclusão: {}", id);
            throw new ResourceNotFoundException("Produto não encontrado para exclusão: " + id);
        }
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}