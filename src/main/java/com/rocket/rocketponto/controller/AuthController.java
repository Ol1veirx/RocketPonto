package com.rocket.rocketponto.controller;

import com.rocket.rocketponto.dto.AuthRequestDTO;
import com.rocket.rocketponto.dto.RegisterRequestDTO;
import com.rocket.rocketponto.entity.Role;
import com.rocket.rocketponto.entity.User;
import com.rocket.rocketponto.repositories.RoleRepository;
import com.rocket.rocketponto.repositories.UserRepository;
import com.rocket.rocketponto.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final com.rocket.rocketponto.security.UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final RoleRepository roleRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, com.rocket.rocketponto.security.UserDetailsServiceImpl userDetailsService, UserRepository userRepository, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequestDTO authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        User userId = userRepository.findByEmail(authRequest.getUsername()).get();
        return jwtUtil.generateToken(userDetails.getUsername(), userId.getId(), new ArrayList<>(userId.getRoles()));
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequestDTO registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setPosition(registerRequest.getPosition());
        user.setDepartment(registerRequest.getDepartment());
        user.setActive(true);
        user.setCreatedDate(LocalDateTime.now());

        Role role = roleRepository.findByName("ROLE_OPERATOR");
        user.setRoles(Set.of(role));

        userRepository.save(user);

        return "Usuário registrado com sucesso!";
    }
}