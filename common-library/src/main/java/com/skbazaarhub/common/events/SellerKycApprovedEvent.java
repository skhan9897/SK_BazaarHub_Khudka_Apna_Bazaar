package com.skbazaarhub.common.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerKycApprovedEvent {
    private Long sellerId;
    private String businessName;
    private String email;
    private String approvedBy;
    private LocalDateTime timestamp;
}
