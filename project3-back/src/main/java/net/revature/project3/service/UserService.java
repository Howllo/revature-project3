package net.revature.project3.service;

import net.revature.project3.entity.AppUser;
import net.revature.project3.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public Optional<AppUser> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public boolean existsByEmail(String email) { return userRepo.existsByEmail(email); }
}
