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
@Table(name="app_user")
public class AppUser {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "profile_pic")
    private String profilePic;

    @Column(name = "role")
    private String userRole = "ROLE_USER";

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @ManyToMany
    @JoinTable(
            name = "user_favorite",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private final Set<Product> products = new HashSet<>();

    public AppUser() {}

    public AppUser(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public AppUser(Long id, String email, String profilePic, String name) {
        this.id = id;
        this.email = email;
        this.profilePic = profilePic;
        this.name = name;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + name + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", userRole='" + userRole + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
