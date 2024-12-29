package com.scm.scm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {
    private String productId;
    private String name;
    private int quantity;
    private Double unitPrice;

}
