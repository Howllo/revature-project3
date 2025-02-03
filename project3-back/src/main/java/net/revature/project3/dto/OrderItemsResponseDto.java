package net.revature.project3.dto;

import net.revature.project3.entity.Product;

public record OrderItemsResponseDto(Long orderHistoryId, Product product, Long quantity, Double price) { }
