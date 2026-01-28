package com.sparta.order_management_service.domain.order.dto;

import lombok.Getter;

@Getter
public class OrderRequestDto {

    // TODO 유효성 검사

    private Long productId;
    private Long quantity;
    private String status;
}
