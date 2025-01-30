package net.revature.project3.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
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

    public UserCart(Long id, AppUser userId, Product productId, Long quantity, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getUserId() {
        return userId;
    }

    public void setUserId(AppUser userId) {
        this.userId = userId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
