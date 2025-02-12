package net.revature.project3.repository;

import net.revature.project3.dto.CartItemResponseDto;
import net.revature.project3.entity.UserCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<UserCart, Long> {
    @Query("select c from UserCart c where c.userId.id = :userId and c.productId.id = :productId")
    Optional<UserCart> findUserCartByUserIdAndProductId(@Param("userId") Long userId,
                                                               @Param("productId") Long productId);

    @Query("select new net.revature.project3.dto.CartItemResponseDto(c.id, c.userId.id, c.productId.id, c.quantity, " +
            "c.productId.price, c.productId.description, c.productId.pic) from UserCart c where c.userId.id = :userId")
    List<CartItemResponseDto> findUserCartByUserId(@Param("userId") Long userId);
}
