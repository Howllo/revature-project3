package net.revature.project3.service;

import net.revature.project3.dto.OrderHistoryResponseDto;
import net.revature.project3.dto.OrderHistoryUserRequestDto;
import net.revature.project3.entity.AppUser;
import net.revature.project3.repository.OrderHistoryRepo;
import net.revature.project3.security.JwtTokenUtil;
import net.revature.project3.utils.TokenValidationCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderHistoryService {
    private final OrderHistoryRepo orderHistoryRepo;
    private final TokenValidationCheck tokenValidationCheck;

    @Autowired
    public OrderHistoryService(OrderHistoryRepo orderHistoryRepo, TokenValidationCheck tokenValidationCheck) {
        this.orderHistoryRepo = orderHistoryRepo;
        this.tokenValidationCheck = tokenValidationCheck;
    }

    /**
     * Get order histories based on the start and end dates.
     * @param userId Takes in a user id to grab information.
     * @param orderRequest Take in Dto that will be used for getting all the dates.
     * @return All the order histories between two dates.
     */
    public List<OrderHistoryResponseDto> getAllUserOrderHistory(Long userId, OrderHistoryUserRequestDto orderRequest,
                                                                String token) {
        if(token == null || token.isEmpty() || !tokenValidationCheck.isValidToken(token, userId)){
            return new ArrayList<>();
        }

        return orderHistoryRepo.findOrderHistoryByUserIdBetween(userId, orderRequest.startDate(), orderRequest.endDate());
    }
}
