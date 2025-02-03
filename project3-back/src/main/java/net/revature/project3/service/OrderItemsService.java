package net.revature.project3.service;

import net.revature.project3.dto.OrderItemRequestDto;
import net.revature.project3.dto.OrderItemsResponseDto;
import net.revature.project3.repository.OrderItemsRepo;
import net.revature.project3.utils.TokenValidationCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemsService {
    private final OrderItemsRepo orderItemsRepo;
    private final TokenValidationCheck tokenValidationCheck;

    @Autowired
    public OrderItemsService(OrderItemsRepo orderItemsRepo, UserService userService,
                             TokenValidationCheck tokenValidationCheck) {
        this.orderItemsRepo = orderItemsRepo;
        this.tokenValidationCheck = tokenValidationCheck;
    }

    public List<OrderItemsResponseDto> getAllOrderItems(Long id, OrderItemRequestDto itemRequestDto, String token) {
        if(token == null || token.isEmpty() || itemRequestDto == null || !tokenValidationCheck.isValidToken(token, id)) {
            return new ArrayList<>();
        }
        return orderItemsRepo.findByOrderIdIn(itemRequestDto.orderHistoryIds());
    }
}
