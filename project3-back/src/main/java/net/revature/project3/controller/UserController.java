package net.revature.project3.controller;

import net.revature.project3.dto.UserReturnResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @GetMapping("/{id}")
    public ResponseEntity<UserReturnResponseDto> getUser(@PathVariable int id) {

    }
}
