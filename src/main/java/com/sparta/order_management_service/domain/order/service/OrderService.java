package com.sparta.order_management_service.domain.order.service;

import com.sparta.order_management_service.domain.order.dto.OrderRequestDto;
import com.sparta.order_management_service.domain.order.dto.OrderResponseDto;
import com.sparta.order_management_service.domain.order.entity.OrderEntity;
import com.sparta.order_management_service.domain.order.repository.OrderRepository;
import com.sparta.order_management_service.domain.product.entity.ProductEntity;
import com.sparta.order_management_service.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    /// 주문 등록
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {

        ProductEntity productEntity = productRepository.findByIdWithLock(requestDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + requestDto.getProductId()));

        productEntity.decreaseStock(requestDto.getQuantity());

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
    public OrderResponseDto getOrderById(Long id) {
        OrderEntity orderEntity = findOrderOrThrow(id);
        return OrderResponseDto.fromEntity(orderEntity);
    }

    /// 주문 목록 조회
    public Page<OrderResponseDto> getAllOrders(Pageable pageable) {
        Page<OrderEntity> orderEntities = orderRepository.findAllWithProduct(pageable);
        return orderEntities.map(OrderResponseDto::fromEntity);
    }

    /// 주문 수정
    @Transactional
    public OrderResponseDto updateOrder(Long id, OrderRequestDto requestDto) {
        OrderEntity orderEntity = findOrderOrThrow(id);

        Long oldQuantity = orderEntity.getQuantity();
        Long newQuantity = requestDto.getQuantity();

        if (!oldQuantity.equals(newQuantity)) {
            ProductEntity productEntity = productRepository.findByIdWithLock(orderEntity.getProductEntity().getId())
                    .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

            long quantityDiff = newQuantity - oldQuantity;

            if (quantityDiff > 0) {
                productEntity.decreaseStock(quantityDiff);
            } else {
                productEntity.increaseStock(Math.abs(quantityDiff));
            }
        }

        orderEntity.update(newQuantity, requestDto.getStatus());
        return OrderResponseDto.fromEntity(orderRepository.save(orderEntity));
    }

    /// 주문 삭제
    @Transactional
    public void deleteOrder(Long id) {
        OrderEntity orderEntity = findOrderOrThrow(id);
        ProductEntity productEntity = productRepository.findByIdWithLock(orderEntity.getProductEntity().getId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        productEntity.increaseStock(orderEntity.getQuantity());

        orderRepository.delete(orderEntity);
    }

    private OrderEntity findOrderOrThrow(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다. ID: " + id));
    }
}