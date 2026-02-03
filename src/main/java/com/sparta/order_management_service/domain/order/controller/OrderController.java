package com.sparta.order_management_service.domain.order.controller;

import com.sparta.order_management_service.domain.order.dto.OrderRequestDto;
import com.sparta.order_management_service.domain.order.dto.OrderResponseDto;
import com.sparta.order_management_service.domain.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "주문 관리 API")
public class OrderController {

    private final OrderService orderService;

    /// 주문 등록
    @Operation(summary = "주문 등록", description = "새로운 주문을 등록합니다.")
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @Valid @RequestBody OrderRequestDto requestDto
    ) {
        OrderResponseDto responseDto = orderService.createOrder(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    /// 주문 단건 조회
    @Operation(summary = "주문 단건 조회", description = "ID로 특정 주문을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(
            @PathVariable Long id
    ) {
        OrderResponseDto responseDto = orderService.getOrderById(id);
        return ResponseEntity.ok(responseDto);
    }

    /// 주문 목록 조회
    @Operation(summary = "주문 목록 조회", description = "전체 주문 목록을 페이지네이션으로 조회합니다.")
    @GetMapping
    public ResponseEntity<Page<OrderResponseDto>> getAllOrders(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<OrderResponseDto> responseDtoPage = orderService.getAllOrders(pageable);
        return ResponseEntity.ok(responseDtoPage);
    }

    /// 주문 수정
    @Operation(summary = "주문 수정", description = "ID로 특정 주문을 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody OrderRequestDto requestDto
    ) {
        OrderResponseDto responseDto = orderService.updateOrder(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    /// 주문 삭제
    @Operation(summary = "주문 삭제", description = "ID로 특정 주문을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable Long id
    ) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}