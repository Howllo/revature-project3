package net.revature.project3.service;

import net.revature.project3.dto.CartItemResponseDto;
import net.revature.project3.dto.CartRequestDto;
import net.revature.project3.entity.AppUser;
import net.revature.project3.entity.Product;
import net.revature.project3.entity.UserCart;
import net.revature.project3.enumerator.CartEnum;
import net.revature.project3.repository.CartRepo;
import net.revature.project3.repository.UserRepo;
import net.revature.project3.result.Result;
import net.revature.project3.utils.TokenValidationCheck;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepo cartRepo;
    private final ProductService productService;
    private final UserService userService;
    private final TokenValidationCheck tokenValidationCheck;
    private final UserRepo userRepo;

    public CartService(CartRepo cartRepo, ProductService productService, UserService userService,
                       TokenValidationCheck tokenValidationCheck, UserRepo userRepo) {
        this.cartRepo = cartRepo;
        this.productService = productService;
        this.userService = userService;
        this.tokenValidationCheck = tokenValidationCheck;
        this.userRepo = userRepo;
    }

    public Result<CartEnum, List<CartItemResponseDto>> getCart(Long id, String token) {
        if(!tokenValidationCheck.isValidToken(token, id)){
            return new Result<>(CartEnum.UNAUTHORIZED, null, "Unauthorized to do this operation");
        }

        List<CartItemResponseDto> userCartList = cartRepo.findUserCartByUserId(id);
        return new Result<>(CartEnum.SUCCESS, userCartList, "Successful!");
    }

    @Transactional
    public Result<CartEnum, Product> addCart(Long id, CartRequestDto requestDto, String token){
        if(!tokenValidationCheck.isValidToken(token, id)){
            return new Result<>(CartEnum.UNAUTHORIZED, null, "Unauthorized to do this operation");
        }

        if(requestDto.productId() <= 0){
            return new Result<>(CartEnum.BAD_REQUEST, null, "Product id must be greater than 0");
        }

        if(requestDto.quantity() <= 0){
            return new Result<>(CartEnum.BAD_REQUEST, null, "Quantity must be greater than 0");
        }

        Optional<AppUser> appUserOptional = userService.findById(id);
        if(appUserOptional.isEmpty()){
            return new Result<>(CartEnum.BAD_REQUEST, null, "User not found");
        }
        AppUser appUser = appUserOptional.get();

        Optional<Product> optionalProduct = productService.getProduct(requestDto.productId());
        if(optionalProduct.isEmpty()){
            return new Result<>(CartEnum.BAD_REQUEST, null, "Product not found");
        }
        Product product = optionalProduct.get();

        UserCart userCart = new UserCart(appUser, product, requestDto.quantity());
        cartRepo.save(userCart);

        return new Result<>(CartEnum.SUCCESS, product, "Added cart successfully");
    }

    @Transactional
    public Result<CartEnum, UserCart> removeCart(Long id, Long cartItemId, String token){
        if(!tokenValidationCheck.isValidToken(token, id)){
            return new Result<>(CartEnum.UNAUTHORIZED, null, "Unauthorized to do this operation");
        }

        Optional<UserCart> cartOptional = cartRepo.findById(cartItemId);
        if(cartOptional.isEmpty()){
            return new Result<>(CartEnum.BAD_REQUEST, null, "User not found");
        }
        UserCart userCart = cartOptional.get();

        cartRepo.delete(userCart);
        return new Result<>(CartEnum.SUCCESS, null, "Removed cart successfully");
    }

    @Transactional
    public Result<CartEnum, UserCart> updateCart(Long id, Long cartItemId, Long quantity, String token){
        if(!tokenValidationCheck.isValidToken(token, id)){
            return new Result<>(CartEnum.UNAUTHORIZED, null, "Unauthorized to do this operation");
        }

        Optional<UserCart> cartOptional = cartRepo.findById(cartItemId);
        if(cartOptional.isEmpty()){
            return new Result<>(CartEnum.BAD_REQUEST, null, "User not found");
        }
        UserCart userCart = cartOptional.get();

        if(quantity == 0){
            cartRepo.delete(userCart);
            return new Result<>(CartEnum.SUCCESS, null, "Removed from cart successfully.");
        }

        userCart.setQuantity(quantity);
        cartRepo.save(userCart);
        return new Result<>(CartEnum.SUCCESS, null, "Updated cart successfully");
    }
}