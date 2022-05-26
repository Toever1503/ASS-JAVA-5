package com.service.dto;

import com.model.Product;
import lombok.Data;

@Data
public class CartDetailsDto {
    private Product product;
    private Integer quantity;
    private Integer price;
}
