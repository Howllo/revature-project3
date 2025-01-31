package net.revature.project3.dto;

import java.sql.Timestamp;

public record OrderHistoryUserRequestDto(Timestamp startDate, Timestamp endDate) { }
