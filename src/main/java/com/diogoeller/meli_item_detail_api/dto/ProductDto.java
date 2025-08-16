package com.diogoeller.meli_item_detail_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
@Schema(description = "Objeto de transferência de dados para Produto")
public class ProductDto {
    @Schema(description = "ID do produto", example = "MLB123")
    private String id;

    @Schema(description = "Título do produto", example = "Smartphone Samsung Galaxy S21")
    private String title;

    @Schema(description = "Descrição do produto", example = "Celular Samsung Galaxy S21 128GB")
    private String description;

    @Schema(description = "Preço do produto", example = "3499.99")
    private BigDecimal price;

    @Schema(description = "Categoria do produto", example = "Eletrônicos")
    private String category;

    @Schema(description = "Vendedor do produto", example = "SamsungStore")
    private String seller;

    @Schema(description = "Quantidade disponível", example = "10")
    private Integer availableQuantity;

    @Schema(description = "URLs das imagens do produto")
    private List<String> pictures;
}