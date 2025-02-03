package net.revature.project3.repository;

import net.revature.project3.entity.UserCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<UserCart, Long> {
}
