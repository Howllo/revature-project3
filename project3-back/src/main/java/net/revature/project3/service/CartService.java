package net.revature.project3.service;

import net.revature.project3.dto.CartRequestDto;
import net.revature.project3.entity.AppUser;
import net.revature.project3.entity.Product;
import net.revature.project3.entity.UserCart;
import net.revature.project3.enumerator.CartEnum;
import net.revature.project3.repository.CartRepo;
import net.revature.project3.result.Result;
import net.revature.project3.utils.TokenValidationCheck;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartService {
    private final CartRepo cartRepo;
    private final ProductService productService;
    private final UserService userService;
    private final TokenValidationCheck tokenValidationCheck;

    public CartService(CartRepo cartRepo, ProductService productService, UserService userService,
                       TokenValidationCheck tokenValidationCheck) {
        this.cartRepo = cartRepo;
        this.productService = productService;
        this.userService = userService;
        this.tokenValidationCheck = tokenValidationCheck;
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

    public Result<CartEnum, Product> removeCart(Long id, CartRequestDto requestDto, String token){
        if(!tokenValidationCheck.isValidToken(token, id)){
            return new Result<>(CartEnum.UNAUTHORIZED, null, "Unauthorized to do this operation");
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

        cartRepo.delete(userCart);
        return new Result<>(CartEnum.SUCCESS, product, "Removed cart successfully");
    }
}