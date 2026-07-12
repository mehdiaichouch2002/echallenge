package com.echallenge.service.impl;

import com.echallenge.dto.AuthResponse;
import com.echallenge.dto.LoginRequest;
import com.echallenge.dto.RegisterRequest;
import com.echallenge.entity.Candidate;
import com.echallenge.entity.User;
import com.echallenge.repository.CandidateRepository;
import com.echallenge.repository.UserRepository;
import com.echallenge.security.JwtService;
import com.echallenge.service.AuthService;
import com.echallenge.service.exception.BusinessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final CandidateRepository candidateRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository,
                           CandidateRepository candidateRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService) {
        this.userRepository = userRepository;
        this.candidateRepository = candidateRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("User not found"));
        return buildResponse(user);
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("An account already exists with this email");
        }

        Candidate candidate = new Candidate();
        candidate.setEmail(request.getEmail());
        candidate.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        candidate.setRole(User.Role.CANDIDATE);
        candidate.setFirstName(request.getFirstName());
        candidate.setLastName(request.getLastName());
        candidate.setSchool(request.getSchool());
        candidate.setField(request.getField());
        candidate.setGsm(request.getGsm());
        candidate.setCode("CAND-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        candidateRepository.save(candidate);

        return buildResponse(candidate);
    }

    private AuthResponse buildResponse(User user) {
        String fullName = user instanceof Candidate c
                ? c.getFirstName() + " " + c.getLastName()
                : "Administrator";
        String token = jwtService.generateToken(user.getEmail(), user.getRole().name(), user.getId());
        return new AuthResponse(token, user.getId(), user.getEmail(), user.getRole().name(), fullName);
    }
}
