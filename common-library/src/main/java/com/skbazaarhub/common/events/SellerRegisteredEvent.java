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
public class SellerRegisteredEvent {
    private Long sellerId;
    private Long userId;
    private String businessName;
    private String email;
    private String mobile;
    private String gstin;
    private LocalDateTime timestamp;
}
