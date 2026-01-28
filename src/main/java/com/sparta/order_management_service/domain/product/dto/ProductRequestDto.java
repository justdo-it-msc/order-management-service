package com.sparta.order_management_service.domain.product.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductRequestDto {

    // TODO 유효성 검사

    private String name;
    private BigDecimal price;
    private Long stock;
}
