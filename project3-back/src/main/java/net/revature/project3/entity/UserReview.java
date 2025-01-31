package net.revature.project3.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter @Setter
@Table(name = "user_review")
public class UserReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "rating")
    private Short rating;

    @Column(name = "description")
    private String description;

    @Column(name = "post_at")
    private Timestamp postAt;

    public UserReview() {}

    public UserReview(long id, AppUser user, Product product, Short rating, String description, Timestamp postAt) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.rating = rating;
        this.description = description;
        this.postAt = postAt;
    }
}
