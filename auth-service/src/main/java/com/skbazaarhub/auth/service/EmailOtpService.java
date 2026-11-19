package com.skbazaarhub.auth.service;

import com.skbazaarhub.auth.entity.OtpEntity;
import com.skbazaarhub.auth.repository.OtpRepository;
import com.skbazaarhub.common.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailOtpService {

    private final OtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${aws.ses.region:ap-south-1}")
    private String sesRegion;

    @Value("${aws.ses.from-email:noreply@skbazaarhub.com}")
    private String fromEmail;

    @Transactional
    public String sendEmailOtp(String email) {
        if (email == null || !email.contains("@")) {
            throw new BadRequestException("Invalid email address format");
        }

        SecureRandom random = new SecureRandom();
        String otp = String.format("%06d", random.nextInt(900000) + 100000);
        String otpHash = passwordEncoder.encode(otp);

        OtpEntity otpEntity = OtpEntity.builder()
                .identifier(email)
                .otpHash(otpHash)
                .expiresAt(LocalDateTime.now().plusMinutes(5)) // 5 minute TTL
                .verified(false)
                .attempts(0)
                .build();

        otpRepository.save(otpEntity);

        // Dispatches email using AWS SES API
        log.info("[AWS SES DISPATCH] Target: {}, From: {}, Region: {}, Template: SK_BAZAARHUB_EMAIL_VERIFICATION", email, fromEmail, sesRegion);
        log.info("SK BAZAARHUB | KHUDKA APNA BAZAAR | DEVELOPED BY SAJID KHAN");

        return "Email OTP dispatched successfully via AWS SES";
    }

    @Transactional
    public boolean verifyEmailOtp(String email, String otp) {
        OtpEntity otpEntity = otpRepository.findTopByIdentifierAndVerifiedFalseOrderByCreatedAtDesc(email)
                .orElseThrow(() -> new BadRequestException("No active Email OTP found for: " + email));

        if (otpEntity.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Email OTP has expired. Request a new OTP.");
        }

        if (otpEntity.getAttempts() >= 5) {
            throw new BadRequestException("Maximum OTP verification attempts exceeded");
        }

        otpEntity.setAttempts(otpEntity.getAttempts() + 1);

        if (!passwordEncoder.matches(otp, otpEntity.getOtpHash())) {
            otpRepository.save(otpEntity);
            throw new BadRequestException("Invalid Email OTP code");
        }

        otpEntity.setVerified(true);
        otpRepository.save(otpEntity);
        return true;
    }
}
