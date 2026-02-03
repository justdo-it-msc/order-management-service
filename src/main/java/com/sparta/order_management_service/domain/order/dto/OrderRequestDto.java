package com.sparta.order_management_service.domain.order.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class OrderRequestDto {

    @NotNull(message = "상품 ID는 필수입니다.")
    @Positive(message = "상품 ID는 양수여야 합니다.")
    private Long productId;

    @NotNull(message = "수량은 필수입니다.")
    @Min(value = 1, message = "수량은 최소 1개 이상이어야 합니다.")
    private Long quantity;

    @NotBlank(message = "주문 상태는 필수입니다.")
    @Pattern(regexp = "^(PENDING|CONFIRMED|SHIPPED|DELIVERED|CANCELLED)$",
            message = "주문 상태는 PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED 중 하나여야 합니다.")
    private String status;
}