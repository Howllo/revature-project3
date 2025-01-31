package net.revature.project3.service;

import net.revature.project3.entity.AppUser;
import net.revature.project3.repository.OrderItemsRepo;
import net.revature.project3.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class OrderItemsService {
    private final OrderItemsRepo orderItemsRepo;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public OrderItemsService(OrderItemsRepo orderItemsRepo, UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.orderItemsRepo = orderItemsRepo;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
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
