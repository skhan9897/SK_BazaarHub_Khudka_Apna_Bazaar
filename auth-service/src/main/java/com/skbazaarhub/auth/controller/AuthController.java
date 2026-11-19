package com.skbazaarhub.auth.controller;

import com.skbazaarhub.auth.service.AadhaarVerificationService;
import com.skbazaarhub.auth.service.AuthService;
import com.skbazaarhub.auth.service.EmailOtpService;
import com.skbazaarhub.auth.service.MobileOtpService;
import com.skbazaarhub.common.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final EmailOtpService emailOtpService;
    private final MobileOtpService mobileOtpService;
    private final AadhaarVerificationService aadhaarVerificationService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<LoginResponse>> registerCustomer(@Valid @RequestBody CustomerRegistrationDto dto) {
        LoginResponse response = authService.registerCustomer(dto);
        return ResponseEntity.ok(ApiResponse.success(response, "Customer registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest dto) {
        LoginResponse response = authService.login(dto);
        return ResponseEntity.ok(ApiResponse.success(response, "Login successful"));
    }

    @PostMapping("/email-otp/send")
    public ResponseEntity<ApiResponse<String>> sendEmailOtp(@RequestParam("email") String email) {
        String message = emailOtpService.sendEmailOtp(email);
        return ResponseEntity.ok(ApiResponse.success(message));
    }

    @PostMapping("/email-otp/verify")
    public ResponseEntity<ApiResponse<Boolean>> verifyEmailOtp(@RequestParam("email") String email,
                                                               @RequestParam("otp") String otp) {
        boolean verified = emailOtpService.verifyEmailOtp(email, otp);
        return ResponseEntity.ok(ApiResponse.success(verified, "Email OTP verified successfully"));
    }

    @PostMapping("/mobile-otp/send")
    public ResponseEntity<ApiResponse<String>> sendMobileOtp(@RequestParam("mobile") String mobile) {
        String message = mobileOtpService.sendMobileOtp(mobile);
        return ResponseEntity.ok(ApiResponse.success(message));
    }

    @PostMapping("/mobile-otp/verify")
    public ResponseEntity<ApiResponse<Boolean>> verifyMobileOtp(@RequestParam("mobile") String mobile,
                                                                @RequestParam("otp") String otp) {
        boolean verified = mobileOtpService.verifyMobileOtp(mobile, otp);
        return ResponseEntity.ok(ApiResponse.success(verified, "Mobile OTP verified successfully"));
    }

    @PostMapping("/aadhaar/verify")
    public ResponseEntity<ApiResponse<Map<String, Object>>> verifyAadhaar(@RequestParam("aadhaar") String aadhaar,
                                                                          @RequestParam("otp") String otp) {
        Map<String, Object> result = aadhaarVerificationService.verifyAadhaarOtp(aadhaar, otp);
        return ResponseEntity.ok(ApiResponse.success(result, "Aadhaar verified successfully"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestParam("email") String email) {
        String message = emailOtpService.sendEmailOtp(email);
        return ResponseEntity.ok(ApiResponse.success(message, "Password reset OTP sent to email"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Boolean>> resetPassword(@RequestParam("email") String email,
                                                              @RequestParam("otp") String otp) {
        boolean verified = emailOtpService.verifyEmailOtp(email, otp);
        return ResponseEntity.ok(ApiResponse.success(verified, "Password reset verified"));
    }

    @GetMapping("/verification-status")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getVerificationStatus(@RequestParam("identifier") String identifier) {
        Map<String, Object> status = new HashMap<>();
        status.put("identifier", identifier);
        status.put("emailVerified", true);
        status.put("mobileVerified", true);
        status.put("platform", "SK BAZAARHUB - KHUDKA APNA BAZAAR");
        status.put("developer", "SAJID KHAN");
        return ResponseEntity.ok(ApiResponse.success(status));
    }
}
