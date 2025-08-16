package com.diogoeller.meli_item_detail_api.mapper;

import java.util.List;

import com.diogoeller.meli_item_detail_api.dto.ProductDto;
import com.diogoeller.meli_item_detail_api.model.Product;

public class ProductMapper {

    private ProductMapper() {}

    public static ProductDto toDto(Product product) {
        if (product == null) return null;
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCategory(product.getCategory());
        dto.setSeller(product.getSeller());
        dto.setAvailableQuantity(product.getAvailableQuantity());
        dto.setPictures(product.getPictures());
        return dto;
    }

    public static Product toEntity(ProductDto dto) {
        if (dto == null) return null;
        Product product = new Product();
        product.setId(dto.getId());
        product.setTitle(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());
        product.setSeller(dto.getSeller());
        product.setAvailableQuantity(dto.getAvailableQuantity());
        product.setPictures(dto.getPictures());
        return product;
    }

    public static List<ProductDto> toDtoList(List<Product> products) {
        return products.stream().map(ProductMapper::toDto).toList();
    }

    public static List<Product> toEntityList(List<ProductDto> dtos) {
        return dtos.stream().map(ProductMapper::toEntity).toList();
    }
}