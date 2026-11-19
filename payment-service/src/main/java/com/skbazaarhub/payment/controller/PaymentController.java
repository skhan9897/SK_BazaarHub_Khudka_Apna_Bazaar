package com.skbazaarhub.payment.controller;

import com.skbazaarhub.common.dto.ApiResponse;
import com.skbazaarhub.common.dto.PaymentRequestDto;
import com.skbazaarhub.common.dto.PaymentVerificationDto;
import com.skbazaarhub.payment.entity.PaymentEntity;
import com.skbazaarhub.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-order")
    public ResponseEntity<ApiResponse<PaymentEntity>> createPaymentOrder(@Valid @RequestBody PaymentRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.success(paymentService.createPaymentOrder(dto), "Payment order created"));
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<Boolean>> verifyPayment(@Valid @RequestBody PaymentVerificationDto dto) {
        boolean verified = paymentService.verifyPaymentSignature(dto);
        return ResponseEntity.ok(ApiResponse.success(verified, "Payment authorized and verified successfully"));
    }

    @PostMapping("/webhook")
    public ResponseEntity<ApiResponse<String>> handleWebhook(@RequestBody String payload,
                                                              @RequestHeader("X-Razorpay-Signature") String signature) {
        paymentService.processWebhook(payload, signature);
        return ResponseEntity.ok(ApiResponse.success("Webhook processed successfully"));
    }
}
