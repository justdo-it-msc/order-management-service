package com.sparta.order_management_service.domain.product.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductRequestDto {

    @NotBlank(message = "상품명은 필수입니다.")
    @Size(min = 1, max = 100, message = "상품명은 1자 이상 100자 이하여야 합니다.")
    private String name;

    @NotNull(message = "가격은 필수입니다.")
    @DecimalMin(value = "0.0", inclusive = false, message = "가격은 0보다 커야 합니다.")
    private BigDecimal price;

    @NotNull(message = "재고는 필수입니다.")
    @Min(value = 0, message = "재고는 0 이상이어야 합니다.")
    private Long stock;
}
