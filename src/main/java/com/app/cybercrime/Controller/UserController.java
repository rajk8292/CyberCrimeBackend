package com.app.cybercrime.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.app.cybercrime.entity.User;
import com.app.cybercrime.repository.UserRepository;
import com.app.cybercrime.service.EmailService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {

        // Save user
        User savedUser = userRepo.save(user);

        // Send email
        String subject = "Registration Successful";
        String body = "Hello " + user.getUsername() + ",\n\nYour registration is successful.\n\nThanks,\nCyber Crime Portal";
        emailService.sendSimpleMail(user.getEmail(), subject, body);

        return savedUser;
    }
}

