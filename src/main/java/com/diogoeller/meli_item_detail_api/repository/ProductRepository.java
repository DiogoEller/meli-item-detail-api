package com.diogoeller.meli_item_detail_api.repository;

import com.diogoeller.meli_item_detail_api.exception.ProductAlreadyExistsException;
import com.diogoeller.meli_item_detail_api.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class ProductRepository {

    @Value("${products.file.path:src/main/resources/data/products.json}")
    private String filePath;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private File getFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                objectMapper.writeValue(file, new ArrayList<Product>());
                log.info("Arquivo de produtos criado: {}", filePath);
            } catch (IOException e) {
                log.error("Erro ao criar arquivo de produtos: {}", e.getMessage());
            }
        }
        return file;
    }

    public List<Product> findAll() {
        log.info("Lendo todos os produtos do arquivo JSON.");
        try {
            List<Product> products = objectMapper.readValue(getFile(), new TypeReference<List<Product>>() {});
            log.debug("Produtos carregados: {}", products.size());
            return products;
        } catch (IOException e) {
            log.error("Erro ao ler produtos do arquivo: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    public Product findById(String id) {
        log.info("Buscando produto por id: {}", id);
        Product product = findAll().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (product == null) {
            log.warn("Produto com id '{}' não encontrado.", id);
        } else {
            log.debug("Produto encontrado: {}", product.getTitle());
        }
        return product;
    }

    public Product save(Product product) {
        log.info("Salvando novo produto: {}", product.getTitle());
        List<Product> products = findAll();
        boolean exists = products.stream().anyMatch(p -> p.getId().equals(product.getId()));
        if (exists) {
            log.warn("Produto com id '{}' já existe. Não será salvo novamente.", product.getId());
            throw new ProductAlreadyExistsException("Produto com id '" + product.getId() + "' já existe. Não será salvo novamente.");
        }
        products.add(product);
        saveAll(products);
        log.debug("Produto salvo com id: {}", product.getId());
        return product;
    }

    public Product update(String id, Product updatedProduct) {
        log.info("Atualizando produto com id: {}", id);
        List<Product> products = findAll();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                updatedProduct.setId(id); // Garante que o id não seja alterado
                products.set(i, updatedProduct);
                saveAll(products);
                log.debug("Produto atualizado: {}", updatedProduct.getTitle());
                return updatedProduct;
            }
        }
        log.warn("Produto com id '{}' não encontrado para atualização.", id);
        return null;
    }

    public void delete(String id) {
        log.info("Removendo produto com id: {}", id);
        List<Product> products = findAll();
        boolean removed = products.removeIf(product -> product.getId().equals(id));
        saveAll(products);
        if (removed) {
            log.debug("Produto com id '{}' removido.", id);
        } else {
            log.warn("Produto com id '{}' não encontrado para remoção.", id);
        }
    }

    public void saveAll(List<Product> products) {
        log.info("Persistindo lista de produtos no arquivo JSON.");
        try {
            objectMapper.writeValue(getFile(), products);
            log.debug("Lista de produtos salva com sucesso.");
        } catch (IOException e) {
            log.error("Erro ao salvar produtos no arquivo: {}", e.getMessage());
        }
    }
}