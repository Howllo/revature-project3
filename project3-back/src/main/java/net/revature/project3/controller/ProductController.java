package net.revature.project3.controller;

import net.revature.project3.dto.ProductAddRequestDto;
import net.revature.project3.entity.Product;
import net.revature.project3.enumerator.ProductEnum;
import net.revature.project3.result.Result;
import net.revature.project3.service.ProductService;
import net.revature.project3.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProduct() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("/new")
    public ResponseEntity<?> createProduct(@RequestBody ProductAddRequestDto product,
                                                 @RequestHeader("Authorization") String token) {
        Result<ProductEnum, Product> result = productService.addProduct(product, token.substring(7));
        return ResponseHandler.returnType(result.result(), result.data(), result.message());
    }
}
