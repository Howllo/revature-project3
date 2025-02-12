package net.revature.project3.dto;

public record CartItemResponseDto(Long id, Long userId, Long productId, Long quantity, Double price,
                                  String description, String imageUrl) {
}
