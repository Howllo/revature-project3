package net.revature.project3.dto;

import net.revature.project3.entity.Product;

public record ProductAddRequestDto(Long userId, Product product) { }
