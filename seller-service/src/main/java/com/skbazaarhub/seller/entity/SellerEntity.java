package com.skbazaarhub.seller.entity;

import com.skbazaarhub.common.enums.KycStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sellers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "business_name", nullable = false, length = 200)
    private String businessName;

    @Column(nullable = false, unique = true, length = 20)
    private String gstin;

    @Column(name = "pan_number", length = 20)
    private String panNumber;

    @Column(name = "contact_person_name", nullable = false, length = 150)
    private String contactPersonName;

    @Enumerated(EnumType.STRING)
    @Column(name = "kyc_status", length = 30)
    @Builder.Default
    private KycStatus kycStatus = KycStatus.PENDING;

    @Column(name = "store_logo_url", length = 500)
    private String storeLogoUrl;

    @Column(name = "store_banner_url", length = 500)
    private String storeBannerUrl;

    @Column(name = "commission_rate", precision = 5, scale = 2)
    @Builder.Default
    private BigDecimal commissionRate = new BigDecimal("5.00");

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
