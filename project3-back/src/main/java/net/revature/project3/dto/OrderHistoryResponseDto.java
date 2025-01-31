package net.revature.project3.dto;

import java.sql.Timestamp;

public record OrderHistoryResponseDto(Long orderId, Double totalPrice, String status, Timestamp orderAt) { }
