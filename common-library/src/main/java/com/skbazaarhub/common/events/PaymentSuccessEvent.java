package com.skbazaarhub.common.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSuccessEvent {
    private Long paymentId;
    private Long orderId;
    private String razorpayPaymentId;
    private BigDecimal amount;
    private String paymentMethod;
    private LocalDateTime timestamp;
}
