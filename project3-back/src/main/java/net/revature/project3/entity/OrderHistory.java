package net.revature.project3.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "order_history")
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private AppUser user;

    @Column(nullable = false, name = "total_price")
    private double totalPrice;

    @Column(nullable = false, name = "status")
    private String status;

    @Column(nullable = false, name = "created_at")
    private Timestamp createdAt;

    public OrderHistory() {}

    public OrderHistory(AppUser user, double totalPrice, String status) {}

    public OrderHistory(Long id, AppUser user, double totalPrice, String status, Timestamp createdAt) {
        this.id = id;
        this.user = user;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
