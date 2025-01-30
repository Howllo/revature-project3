package net.revature.project3.repository;

import net.revature.project3.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepo extends JpaRepository<AppUser, Long> {
}
