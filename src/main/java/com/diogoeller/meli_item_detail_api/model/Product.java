package com.diogoeller.meli_item_detail_api.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class Product {
    private String id;
    private String title;
    private String description;
    private BigDecimal price;
    private String category;
    private String seller;
    private Integer availableQuantity;
    private List<String> pictures;
}