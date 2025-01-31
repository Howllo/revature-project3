package net.revature.project3.dto;

import net.revature.project3.entity.Product;

public record OrderItemsResponseDto(Product product, int quantity, Double price) { }
