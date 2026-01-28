package com.sparta.order_management_service.domain.order.dto;

import com.sparta.order_management_service.domain.order.entity.OrderEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class OrderResponseDto {

    private Long id;
    private Long productId;
    private String productName;
    private Long quantity;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderResponseDto fromEntity(OrderEntity orderEntity) {

        return OrderResponseDto.builder()
                .id(orderEntity.getId())
                .productId(orderEntity.getProductEntity().getId())
                .productName(orderEntity.getProductEntity().getName())
                .quantity(orderEntity.getQuantity())
                .status(orderEntity.getStatus())
                .createdAt(orderEntity.getCreatedAt())
                .updatedAt(orderEntity.getUpdatedAt())
                .build();
    }
}
