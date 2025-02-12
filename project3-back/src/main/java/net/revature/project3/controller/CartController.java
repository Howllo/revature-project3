package net.revature.project3.controller;

import net.revature.project3.dto.CartItemResponseDto;
import net.revature.project3.dto.CartRequestDto;
import net.revature.project3.entity.Product;
import net.revature.project3.entity.UserCart;
import net.revature.project3.enumerator.CartEnum;
import net.revature.project3.result.Result;
import net.revature.project3.service.CartService;
import net.revature.project3.utils.ResponseHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> addCart(@PathVariable Long id,
                                     @RequestBody CartRequestDto cartRequestDto,
                                     @RequestHeader("Authorization") String token) {
        Result<CartEnum, Product> result = cartService.addCart(id, cartRequestDto, token);
        return ResponseHandler.returnType(result.result(), result.data(), result.message());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCart(@PathVariable Long id,
                                     @RequestHeader("Authorization") String token) {
        Result<CartEnum, List<CartItemResponseDto>> result = cartService.getCart(id, token);
        return ResponseHandler.returnType(result.result(), result.data(), result.message());
    }

    @DeleteMapping("/{id}/cart-item/{cartItemId}")
    public ResponseEntity<?> removeCart(@PathVariable Long id,
                                     @PathVariable Long cartItemId,
                                     @RequestHeader("Authorization") String token) {
        Result<CartEnum, UserCart> userCartResult = cartService.removeCart(id, cartItemId, token.substring(7));
        return ResponseHandler.returnType(userCartResult.result(), userCartResult.data(), userCartResult.message());
    }

    @PutMapping("/{id}/cart-item/{cartItemId}")
    public ResponseEntity<?> updateCart(@PathVariable Long id,
                                        @PathVariable Long cartItemId,
                                        @RequestBody Long quantity,
                                        @RequestHeader("Authorization") String token) {
        Result<CartEnum, UserCart> result = cartService.updateCart(id, cartItemId, quantity, token.substring(7));
        return ResponseHandler.returnType(result.result(), result.data(), result.message());
    }
}
