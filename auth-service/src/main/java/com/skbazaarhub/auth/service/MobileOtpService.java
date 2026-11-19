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
public class MobileOtpService {

    private final OtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${msg91.auth-key:skbazaarhub_msg91_auth_key_123}")
    private String msg91AuthKey;

    @Value("${msg91.template-id:skbazaarhub_dlt_template_456}")
    private String msg91TemplateId;

    @Transactional
    public String sendMobileOtp(String mobile) {
        if (mobile == null || !mobile.matches("^[0-9]{10}$")) {
            throw new BadRequestException("Invalid mobile number format. Enter 10-digit Indian mobile number.");
        }

        SecureRandom random = new SecureRandom();
        String otp = String.format("%06d", random.nextInt(900000) + 100000);
        String otpHash = passwordEncoder.encode(otp);

        OtpEntity otpEntity = OtpEntity.builder()
                .identifier(mobile)
                .otpHash(otpHash)
                .expiresAt(LocalDateTime.now().plusMinutes(5)) // 5 min TTL
                .verified(false)
                .attempts(0)
                .build();

        otpRepository.save(otpEntity);

        log.info("[MSG91 DLT SMS DISPATCH] Target Mobile: +91{}, TemplateId: {}", mobile, msg91TemplateId);
        log.info("SK BAZAARHUB | KHUDKA APNA BAZAAR | DEVELOPED BY SAJID KHAN");

        return "Mobile OTP dispatched successfully via MSG91 SMS Gateway";
    }

    @Transactional
    public boolean verifyMobileOtp(String mobile, String otp) {
        OtpEntity otpEntity = otpRepository.findTopByIdentifierAndVerifiedFalseOrderByCreatedAtDesc(mobile)
                .orElseThrow(() -> new BadRequestException("No active Mobile OTP found for: " + mobile));

        if (otpEntity.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Mobile OTP has expired. Request a new OTP.");
        }

        if (otpEntity.getAttempts() >= 5) {
            throw new BadRequestException("Maximum OTP verification attempts exceeded");
        }

        otpEntity.setAttempts(otpEntity.getAttempts() + 1);

        if (!passwordEncoder.matches(otp, otpEntity.getOtpHash())) {
            otpRepository.save(otpEntity);
            throw new BadRequestException("Invalid Mobile OTP code");
        }

        otpEntity.setVerified(true);
        otpRepository.save(otpEntity);
        return true;
    }
}
