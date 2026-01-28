package com.sparta.order_management_service.domain.product.dto;

import com.sparta.order_management_service.domain.product.entity.ProductEntity;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
public class ProductResponseDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private Long stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductResponseDto fromEntity(ProductEntity productEntity) {

        return ProductResponseDto.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .stock(productEntity.getStock())
                .createdAt(productEntity.getCreatedAt())
                .updatedAt(productEntity.getUpdatedAt())
                .build();
    }
}
