package com.example.mockpro.controllers;

import com.example.mockpro.dto.SignInDTO;
import com.example.mockpro.entities.User;
import com.example.mockpro.exceptions.AppError;
import com.example.mockpro.repositories.UserRepository;
import com.example.mockpro.security.JwtCore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtCore jwtCore;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepository userRepository, AuthenticationManager authenticationManager, JwtCore jwtCore, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtCore = jwtCore;
        this.passwordEncoder = passwordEncoder;
    }

        @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignInDTO signupRequest){

        if(userRepository.existsUserByUsername(signupRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Choose different name");
        }
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        String pass = this.passwordEncoder.encode(signupRequest.getPassword());
        user.setPassword(pass);
        userRepository.save(user);
        return ResponseEntity.ok(user.getPassword());
    }

    public @PostMapping("/signin")
    ResponseEntity<?> signin(@RequestBody SignInDTO signinRequest){
        Authentication authentication = null;
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));
        } catch(BadCredentialsException e){
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtCore.generateToken(authentication);
        return ResponseEntity.ok(Collections.singletonMap("token", jwt));
    }
}
