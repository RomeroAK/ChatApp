package org.chatapp.controller;


import org.chatapp.model.AuthenticationRequest;
import org.chatapp.model.AuthenticationResponse;
import org.chatapp.model.User;
import org.chatapp.util.JwtUtil;
import org.chatapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager am;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    UserDetailsService userDetailsService;

    @PostMapping("/register")
    public void registerUser(@RequestBody User user) {
        if(userService.findUserByUsername(user.getUsername()) != null) {
            System.out.println("User already exists");
        }

        user.setRole("USER");
        userService.registerUser(user);
        System.out.println("User registered successfully");

    }

    @PostMapping("/login")
    public void authenticateUser(@RequestBody AuthenticationRequest authRequest) {
        boolean isValid = userService.validateLogin(authRequest.getUsername(), authRequest.getPassword());

        if(isValid){
            System.out.println("Successfully logged in!!");
        }else {
            System.out.println("Username or password invalid!");
        }
    }
}
