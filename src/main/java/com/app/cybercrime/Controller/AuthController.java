package com.app.cybercrime.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.cybercrime.entity.User;
import com.app.cybercrime.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:3000")
public class AuthController {

    @Autowired
    private UserRepository repo;

    @PostMapping("/login")
    public User login(@RequestBody User req) {

        User user = repo.findByUsername(req.getUsername());

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!user.getPassword().equals(req.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // ✅ password match → user return
        return user;
    }
}
