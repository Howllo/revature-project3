package net.revature.project3.repository;

import net.revature.project3.dto.OrderHistoryResponseDto;
import net.revature.project3.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OrderHistoryRepo extends JpaRepository<OrderHistory, Long> {
    @Query("select new net.revature.project3.dto.OrderHistoryResponseDto(oh.id, oh.totalPrice, oh.status, oh.createdAt)" +
            " from OrderHistory oh where oh.user.id = :userId and oh.createdAt between :startDate and :endDate")
    List<OrderHistoryResponseDto> findOrderHistoryByUserIdBetween(@Param("userId") Long userId,
                                                                  @Param("startDate") Timestamp startDate,
                                                                  @Param("endDate") Timestamp endDate);
}
