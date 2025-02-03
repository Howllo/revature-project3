package net.revature.project3.controller;

import net.revature.project3.dto.OrderItemsResponseDto;
import net.revature.project3.dto.OrderItemRequestDto;
import net.revature.project3.service.OrderItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/private/order-items")
public class OrderItemsController {
    private final OrderItemsService orderItemsService;

    @Autowired
    public OrderItemsController(OrderItemsService orderItemsService) {
        this.orderItemsService = orderItemsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<OrderItemsResponseDto>> getOrderItems(@PathVariable Long id,
                                                                     @RequestBody OrderItemRequestDto itemRequestDto,
                                                                     @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(orderItemsService.getAllOrderItems(id, itemRequestDto, token.substring(7)));
    }
}
