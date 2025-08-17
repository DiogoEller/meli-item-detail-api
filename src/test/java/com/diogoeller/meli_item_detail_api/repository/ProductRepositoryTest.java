package com.diogoeller.meli_item_detail_api.repository;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.diogoeller.meli_item_detail_api.exception.ProductAlreadyExistsException;
import com.diogoeller.meli_item_detail_api.model.Product;

class ProductRepositoryTest {

    private static final String TEST_FILE_PATH = "src/test/resources/test-products.json";
    private ProductRepository repo;

    @BeforeEach
    void setUp() {
        // Limpa o arquivo de teste antes de cada teste
        File file = new File(TEST_FILE_PATH);
        file.getParentFile().mkdirs();
        file.delete();
        repo = new ProductRepository(TEST_FILE_PATH);
        repo.saveAll(new ArrayList<>());
    }

    private Product getSampleProduct(String id, String category) {
        Product product = new Product();
        product.setId(id);
        product.setTitle("Test Product " + id);
        product.setDescription("Description");
        product.setPrice(BigDecimal.valueOf(100.0));
        product.setCategory(category);
        product.setSeller("Seller");
        product.setAvailableQuantity(5);
        product.setPictures(List.of("url1", "url2"));
        return product;
    }

    @Test
    void testSaveAndFindById() {
        Product product = getSampleProduct("MLB1", "Eletrônicos");
        repo.save(product);

        Product found = repo.findById("MLB1");
        assertNotNull(found);
        assertEquals("MLB1", found.getId());
    }

    @Test
    void testSaveAlreadyExistsThrowsException() {
        Product product = getSampleProduct("MLB2", "Eletrônicos");
        repo.save(product);

        Product duplicate = getSampleProduct("MLB2", "Eletrônicos");
        ProductAlreadyExistsException exception = assertThrows(ProductAlreadyExistsException.class, () -> repo.save(duplicate));
        assertNotNull(exception);
    }

    @Test
    void testFindAllReturnsAllProducts() {
        repo.save(getSampleProduct("MLB3", "Eletrônicos"));
        repo.save(getSampleProduct("MLB4", "Games"));

        List<Product> products = repo.findAll();
        assertEquals(2, products.size());
    }

    @Test
    void testUpdateProductSuccess() {
        Product product = getSampleProduct("MLB5", "Eletrônicos");
        repo.save(product);

        Product updated = getSampleProduct("MLB5", "Games");
        updated.setTitle("Updated Title");
        Product result = repo.update("MLB5", updated);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Games", result.getCategory());
    }

    @Test
    void testUpdateProductNotFoundReturnsNull() {
        Product updated = getSampleProduct("MLB6", "Games");
        Product result = repo.update("MLB6", updated);
        assertNull(result);
    }

    @Test
    void testDeleteProductSuccess() {
        Product product = getSampleProduct("MLB7", "Eletrônicos");
        repo.save(product);

        repo.delete("MLB7");
        assertNull(repo.findById("MLB7"));
    }

    @Test
    void testDeleteProductNotFoundDoesNothing() {
        assertDoesNotThrow(() -> repo.delete("MLB8"));
    }

    @Test
    void testSaveAllAndFindAll() {
        List<Product> products = List.of(
                getSampleProduct("MLB9", "Eletrônicos"),
                getSampleProduct("MLB10", "Games")
        );
        repo.saveAll(products);

        List<Product> all = repo.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void testFindByCategoryReturnsCorrectProducts() {
        repo.save(getSampleProduct("MLB11", "Eletrônicos"));
        repo.save(getSampleProduct("MLB12", "Games"));
        repo.save(getSampleProduct("MLB13", "Eletrônicos"));

        List<Product> electronics = repo.findByCategory("Eletrônicos");
        assertEquals(2, electronics.size());
        assertTrue(electronics.stream().allMatch(p -> p.getCategory().equalsIgnoreCase("Eletrônicos")));
    }
}
