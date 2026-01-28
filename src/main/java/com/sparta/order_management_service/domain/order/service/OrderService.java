package com.sparta.order_management_service.domain.order.service;

import com.sparta.order_management_service.domain.order.dto.OrderRequestDto;
import com.sparta.order_management_service.domain.order.dto.OrderResponseDto;
import com.sparta.order_management_service.domain.order.entity.OrderEntity;
import com.sparta.order_management_service.domain.order.repository.OrderRepository;
import com.sparta.order_management_service.domain.product.entity.ProductEntity;
import com.sparta.order_management_service.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    /// 주문 등록
    @Transactional
    public OrderResponseDto createOrder(
            OrderRequestDto requestDto
    ) {
        ProductEntity productEntity = findProductOrThrow(requestDto.getProductId());

        OrderEntity orderEntity = orderRepository.save(
                OrderEntity.builder()
                        .productEntity(productEntity)
                        .quantity(requestDto.getQuantity())
                        .status(requestDto.getStatus())
                        .build()
        );

        return OrderResponseDto.fromEntity(orderEntity);
    }

    /// 주문 단건 조회
    public OrderResponseDto getOrderById(
            Long id
    ) {
        OrderEntity orderEntity = findOrderOrThrow(id);
        return OrderResponseDto.fromEntity(orderEntity);
    }

    /// 주문 목록 조회
    @Transactional
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    /// 주문 수정
    @Transactional
    public OrderResponseDto updateOrder(
            Long id,
            OrderRequestDto requestDto
    ) {
        OrderEntity orderEntity = findOrderOrThrow(id);
        orderEntity.update(requestDto.getQuantity(), requestDto.getStatus());
        return OrderResponseDto.fromEntity(orderRepository.save(orderEntity));
    }

    /// 주문 삭제
    @Transactional
    public void deleteOrder(
            Long id
    ) {
        OrderEntity orderEntity = findOrderOrThrow(id);
        orderRepository.delete(orderEntity);
    }

    private ProductEntity findProductOrThrow(
            Long id
    ) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + id));
    }

    private OrderEntity findOrderOrThrow(
            Long id
    ) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다. ID: " + id));
    }
}
