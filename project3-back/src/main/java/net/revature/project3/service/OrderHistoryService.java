package net.revature.project3.service;

import net.revature.project3.dto.OrderHistoryResponseDto;
import net.revature.project3.dto.OrderHistoryUserRequestDto;
import net.revature.project3.entity.AppUser;
import net.revature.project3.repository.OrderHistoryRepo;
import net.revature.project3.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderHistoryService {
    private final OrderHistoryRepo orderHistoryRepo;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public OrderHistoryService(OrderHistoryRepo orderHistoryRepo, UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.orderHistoryRepo = orderHistoryRepo;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * Get order histories based on the start and end dates.
     * @param userId Takes in a user id to grab information.
     * @param orderRequest Take in Dto that will be used for getting all the dates.
     * @return All the order histories between two dates.
     */
    public List<OrderHistoryResponseDto> getAllUserOrderHistory(Long userId, OrderHistoryUserRequestDto orderRequest,
                                                                String token) {
        if(token == null || token.isEmpty() || !isValidToken(token, userId)){
            return new ArrayList<>();
        }

        return orderHistoryRepo.findOrderHistoryByUserIdBetween(userId, orderRequest.startDate(), orderRequest.endDate());
    }

    /**
     * Used to verify information about the user.
     * @param token Take in a token to be processed.
     * @param userId Take in a user id to verify the process.
     * @return Return {@code True} if the token ID and user ID is same or {@code False} if they are not the same.
     */
    public boolean isValidToken(String token, Long userId) {
        Optional<AppUser> optionalAppUser = getUser(token);
        if(optionalAppUser.isEmpty()){
            return false;
        }
        AppUser appUser = optionalAppUser.get();
        return Objects.equals(userId, appUser.getId());
    }

    /**
     * Returns the user of a token if there is a username listed.
     * @param token Take in the JWT token to be processed.
     * @return The AppUser that is associated with the token.
     */
    private Optional<AppUser> getUser(String token) {
        return userService.findByEmail(jwtTokenUtil.getSubjectFromToken(token));
    }
}
