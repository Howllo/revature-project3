package net.revature.project3.repository;

import net.revature.project3.dto.OrderItemsResponseDto;
import net.revature.project3.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemsRepo extends JpaRepository<OrderItems, Long> {
    @Query("SELECT new net.revature.project3.dto.OrderItemsResponseDto(" +
            "o.orderId.id, " +
            "o.productId, " +
            "o.quantity, " +
            "o.priceAtPurchase) " +
            "FROM OrderItems o WHERE o.orderId.id IN :ids")
    List<OrderItemsResponseDto> findByOrderIdIn(@Param("ids") List<Long> ids);
}
