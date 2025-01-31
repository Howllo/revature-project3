package net.revature.project3.controller;

import net.revature.project3.dto.OrderHistoryResponseDto;
import net.revature.project3.dto.OrderHistoryUserRequestDto;
import net.revature.project3.service.OrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/private/order-history")
public class OrderHistoryController {
    private final OrderHistoryService orderHistoryService;

    @Autowired
    public OrderHistoryController(OrderHistoryService orderHistoryService) {
        this.orderHistoryService = orderHistoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<OrderHistoryResponseDto>> getOrderHistory(@PathVariable Long id,
                                                                         @RequestBody OrderHistoryUserRequestDto orderRequest,
                                                                         @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(orderHistoryService.getAllUserOrderHistory(id, orderRequest, token.substring(7)));
    }
}
