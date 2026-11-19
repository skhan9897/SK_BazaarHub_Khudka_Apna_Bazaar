package com.skbazaarhub.common.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpVerificationRequest {

    @NotBlank(message = "Identifier (Email/Mobile) is required")
    private String identifier;

    @NotBlank(message = "OTP is required")
    private String otp;
}
