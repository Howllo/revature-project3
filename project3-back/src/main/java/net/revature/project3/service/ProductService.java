package net.revature.project3.service;

import net.revature.project3.dto.ProductAddRequestDto;
import net.revature.project3.entity.AppUser;
import net.revature.project3.entity.Product;
import net.revature.project3.enumerator.ProductEnum;
import net.revature.project3.repository.ProductRepo;
import net.revature.project3.result.Result;
import net.revature.project3.utils.TokenValidationCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepo productRepo;
    private final TokenValidationCheck tokenValidationCheck;
    private final UserService userService;

    @Autowired
    public ProductService(ProductRepo productRepo, TokenValidationCheck tokenValidationCheck,
                          UserService userService) {
        this.productRepo = productRepo;
        this.tokenValidationCheck = tokenValidationCheck;
        this.userService = userService;
    }

    /**
     * Return all possible product in the system.
     * @return Returns products.
     */
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Result<ProductEnum, Product> addProduct(ProductAddRequestDto productDto, String token) {
        Product product = productDto.product();
        Long id = productDto.userId();

        if(!tokenValidationCheck.isValidToken(token, id)){
            return new Result<>(ProductEnum.UNAUTHORIZED, null, "You are not allowed to add this product");
        }

        if(product.getName().isEmpty() || product.getDescription().isEmpty()){
            return new Result<>(ProductEnum.BAD_REQUEST, null, "Product name or description cannot be empty");
        }

        if(product.getSalePercentage() < 0.0 || product.getSalePercentage() > 100.0){
            return new Result<>(ProductEnum.BAD_REQUEST, null, "Product sale percentage must be between 0 and 100");
        }

        Optional<AppUser> appUserOptional = userService.findById(id);
        if(appUserOptional.isEmpty()){
            return new Result<>(ProductEnum.BAD_REQUEST, null, "User not found");
        }
        AppUser appUser = appUserOptional.get();

        if(!appUser.getUserRole().contains("SELLER") || !appUser.getUserRole().contains("ADMIN")){
            return new Result<>(ProductEnum.UNAUTHORIZED, null, "You are not allowed to add this product");
        }

        productRepo.save(product);
        return new Result<>(ProductEnum.SUCCESS, product, "Product added.");
    }

    public Optional<Product> getProduct(Long id){
        return productRepo.findById(id);
    }
}
