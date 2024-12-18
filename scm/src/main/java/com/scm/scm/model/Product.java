package com.scm.scm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String productId;
    private String name;
    private String description;
    private String category;
    private String material;
    private Double unitPrice;
    private String currency;
    private Integer availableQuantity;
    private Integer minimumOrderQuantity;
    private String supplier;
    private Integer leadTimeDays;
    private Integer weightGram;
}

