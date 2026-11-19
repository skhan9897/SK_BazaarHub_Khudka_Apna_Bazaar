package com.skbazaarhub.auth.controller;

import com.skbazaarhub.auth.service.AuthService;
import com.skbazaarhub.common.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/customer")
    public ResponseEntity<ApiResponse<LoginResponse>> registerCustomer(@Valid @RequestBody CustomerRegistrationDto dto) {
        LoginResponse response = authService.registerCustomer(dto);
        return ResponseEntity.ok(ApiResponse.success(response, "Customer registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest dto) {
        LoginResponse response = authService.login(dto);
        return ResponseEntity.ok(ApiResponse.success(response, "Login successful"));
    }

    @PostMapping("/otp/send")
    public ResponseEntity<ApiResponse<String>> sendOtp(@RequestParam("identifier") String identifier) {
        String message = authService.generateAndSendOtp(identifier);
        return ResponseEntity.ok(ApiResponse.success(message));
    }

    @PostMapping("/otp/verify")
    public ResponseEntity<ApiResponse<Boolean>> verifyOtp(@Valid @RequestBody OtpVerificationRequest dto) {
        boolean verified = authService.verifyOtp(dto);
        return ResponseEntity.ok(ApiResponse.success(verified, "OTP verified successfully"));
    }
}
