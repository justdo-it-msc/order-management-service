package com.sparta.order_management_service.domain.product.controller;

import com.sparta.order_management_service.domain.product.dto.ProductRequestDto;
import com.sparta.order_management_service.domain.product.dto.ProductResponseDto;
import com.sparta.order_management_service.domain.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "상품 관리 API")
public class ProductController {

    private final ProductService productService;

    /// 상품 등록
    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다.")
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(
            @Valid @RequestBody ProductRequestDto requestDto
    ) {
        ProductResponseDto responseDto = productService.createProduct(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    /// 상품 단건 조회
    @Operation(summary = "상품 단건 조회", description = "ID로 특정 상품을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(
            @PathVariable long id
    ) {
        ProductResponseDto responseDto = productService.getProductById(id);
        return ResponseEntity.ok(responseDto);
    }

    /// 상품 목록 조회
    @Operation(summary = "상품 목록 조회", description = "전체 상품 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> responseDtoList = productService.getAllProducts();
        return ResponseEntity.ok(responseDtoList);
    }

    /// 상품 수정
    @Operation(summary = "상품 수정", description = "ID로 특정 상품을 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable long id,
            @Valid @RequestBody ProductRequestDto requestDto
    ) {
        ProductResponseDto responseDto = productService.updateProduct(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    /// 상품 삭제
    @Operation(summary = "상품 삭제", description = "ID로 특정 상품을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id
    ) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
