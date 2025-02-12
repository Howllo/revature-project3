package net.revature.project3.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "product_pic")
    private String pic;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "sale_percentage")
    private Double salePercentage;

    @Column(name = "price")
    private Double price;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @ManyToMany(mappedBy = "favoriteProducts")
    private final Set<AppUser> usersWhoFavorite  = new HashSet<>();

    public Product() {}

    public Product(Long id, String name, String pic, String description, Long quantity, Double salePercentage,
                   Double price, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.pic = pic;
        this.description = description;
        this.quantity = quantity;
        this.salePercentage = salePercentage;
        this.price = price;
        this.createdAt = createdAt;
    }
}
