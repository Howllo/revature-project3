package net.revature.project3.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "user_cart")
public class UserCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser userId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productId;

    @Column(name =  "quantity")
    private Long quantity;

    @Column(name = "created_at")
    private Timestamp createdAt;

    public UserCart() {}

    public UserCart(AppUser userId, Product productId, Long quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
