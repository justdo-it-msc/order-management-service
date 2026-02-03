package com.sparta.order_management_service.domain.product.service;

import com.sparta.order_management_service.domain.product.dto.ProductRequestDto;
import com.sparta.order_management_service.domain.product.dto.ProductResponseDto;
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
public class ProductService {

    private final ProductRepository productRepository;

    /// 상품 등록
    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {

        ProductEntity productEntity = productRepository.save(
                ProductEntity.builder()
                        .name(requestDto.getName())
                        .price(requestDto.getPrice())
                        .stock(requestDto.getStock())
                        .build()
        );

        return ProductResponseDto.fromEntity(productEntity);
    }

    /// 상품 단건 조회
    public ProductResponseDto getProductById(Long id) {
        ProductEntity productEntity = findProductOrThrow(id);
        return ProductResponseDto.fromEntity(productEntity);
    }

    /// 상품 목록 조회
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    /// 상품 수정
    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto) {
        ProductEntity productEntity = findProductOrThrow(id);
        productEntity.update(requestDto.getName(), requestDto.getPrice(), requestDto.getStock());
        return ProductResponseDto.fromEntity(productRepository.save(productEntity));
    }

    /// 상품 삭제
    @Transactional
    public void deleteProduct(Long id) {
        ProductEntity productEntity = findProductOrThrow(id);
        productRepository.delete(productEntity);
    }

    private ProductEntity findProductOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + id));
    }
}
