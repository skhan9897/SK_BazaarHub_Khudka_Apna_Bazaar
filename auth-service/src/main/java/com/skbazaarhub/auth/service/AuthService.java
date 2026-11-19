package com.skbazaarhub.auth.service;

import com.skbazaarhub.auth.entity.*;
import com.skbazaarhub.auth.repository.*;
import com.skbazaarhub.common.dto.*;
import com.skbazaarhub.common.enums.RoleName;
import com.skbazaarhub.common.events.CustomerRegisteredEvent;
import com.skbazaarhub.common.exception.BadRequestException;
import com.skbazaarhub.common.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CustomerRepository customerRepository;
    private final OtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public LoginResponse registerCustomer(CustomerRegistrationDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Email is already registered");
        }
        if (userRepository.existsByMobile(dto.getMobile())) {
            throw new BadRequestException("Mobile number is already registered");
        }

        RoleEntity customerRole = roleRepository.findByName(RoleName.ROLE_CUSTOMER)
                .orElseGet(() -> roleRepository.save(RoleEntity.builder().name(RoleName.ROLE_CUSTOMER).description("Customer purchasing products").build()));

        UserEntity user = UserEntity.builder()
                .email(dto.getEmail())
                .mobile(dto.getMobile())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .enabled(true)
                .verified(false)
                .roles(Set.of(customerRole))
                .build();

        user = userRepository.save(user);

        CustomerEntity customer = CustomerEntity.builder()
                .user(user)
                .build();
        customerRepository.save(customer);

        // Publish Kafka Event
        CustomerRegisteredEvent event = CustomerRegisteredEvent.builder()
                .customerId(customer.getId())
                .userId(user.getId())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .timestamp(LocalDateTime.now())
                .build();
        try {
            kafkaTemplate.send("customer.registered", user.getEmail(), event);
        } catch (Exception ignored) {}

        List<String> roles = user.getRoles().stream().map(r -> r.getName().name()).collect(Collectors.toList());
        String token = tokenProvider.generateToken(user.getEmail(), roles, user.getId());

        return LoginResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getFirstName() + " " + user.getLastName())
                .roles(user.getRoles().stream().map(r -> r.getName().name()).collect(Collectors.toSet()))
                .build();
    }

    public LoginResponse login(LoginRequest dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        UserEntity user = userRepository.findByEmailOrMobile(dto.getUsername(), dto.getUsername())
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));

        List<String> roles = user.getRoles().stream().map(r -> r.getName().name()).collect(Collectors.toList());
        String token = tokenProvider.generateToken(user.getEmail(), roles, user.getId());

        return LoginResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getFirstName() + " " + user.getLastName())
                .roles(user.getRoles().stream().map(r -> r.getName().name()).collect(Collectors.toSet()))
                .build();
    }

    @Transactional
    public String generateAndSendOtp(String identifier) {
        String otp = String.format("%06d", new Random().nextInt(900000) + 100000);
        String otpHash = passwordEncoder.encode(otp);

        OtpEntity otpEntity = OtpEntity.builder()
                .identifier(identifier)
                .otpHash(otpHash)
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .verified(false)
                .attempts(0)
                .build();

        otpRepository.save(otpEntity);

        // In real execution, OTP is dispatched via MSG91/AWS SES. Here we print for system log tracking
        System.out.println(">>> OTP generated for " + identifier + ": " + otp);
        return "OTP sent successfully to " + identifier;
    }

    @Transactional
    public boolean verifyOtp(OtpVerificationRequest dto) {
        OtpEntity otpEntity = otpRepository.findTopByIdentifierAndVerifiedFalseOrderByCreatedAtDesc(dto.getIdentifier())
                .orElseThrow(() -> new BadRequestException("No active OTP found"));

        if (otpEntity.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("OTP has expired");
        }

        if (otpEntity.getAttempts() >= 5) {
            throw new BadRequestException("Maximum OTP attempts exceeded");
        }

        otpEntity.setAttempts(otpEntity.getAttempts() + 1);

        if (!passwordEncoder.matches(dto.getOtp(), otpEntity.getOtpHash())) {
            otpRepository.save(otpEntity);
            throw new BadRequestException("Invalid OTP code");
        }

        otpEntity.setVerified(true);
        otpRepository.save(otpEntity);

        userRepository.findByEmailOrMobile(dto.getIdentifier(), dto.getIdentifier()).ifPresent(user -> {
            user.setVerified(true);
            userRepository.save(user);
        });

        return true;
    }
}
