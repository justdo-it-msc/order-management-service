package com.sparta.order_management_service.domain.order.repository;

import com.sparta.order_management_service.domain.order.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT o FROM OrderEntity o JOIN FETCH o.productEntity")
    Page<OrderEntity> findAllWithProduct(Pageable pageable);
}
