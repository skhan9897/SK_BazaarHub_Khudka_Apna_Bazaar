package com.skbazaarhub.seller.service;

import com.skbazaarhub.common.dto.SellerRegistrationDto;
import com.skbazaarhub.common.enums.KycStatus;
import com.skbazaarhub.common.events.SellerKycApprovedEvent;
import com.skbazaarhub.common.events.SellerRegisteredEvent;
import com.skbazaarhub.common.exception.BadRequestException;
import com.skbazaarhub.common.exception.ResourceNotFoundException;
import com.skbazaarhub.seller.entity.SellerEntity;
import com.skbazaarhub.seller.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public SellerEntity registerSeller(Long userId, SellerRegistrationDto dto) {
        if (sellerRepository.existsByGstin(dto.getGstin())) {
            throw new BadRequestException("GSTIN is already registered");
        }

        SellerEntity seller = SellerEntity.builder()
                .userId(userId)
                .businessName(dto.getBusinessName())
                .gstin(dto.getGstin())
                .panNumber(dto.getPanNumber())
                .contactPersonName(dto.getContactPersonName())
                .kycStatus(KycStatus.SUBMITTED)
                .build();

        seller = sellerRepository.save(seller);

        SellerRegisteredEvent event = SellerRegisteredEvent.builder()
                .sellerId(seller.getId())
                .userId(userId)
                .businessName(seller.getBusinessName())
                .email(dto.getEmail())
                .mobile(dto.getMobile())
                .gstin(seller.getGstin())
                .timestamp(LocalDateTime.now())
                .build();
        try {
            kafkaTemplate.send("seller.registered", seller.getId().toString(), event);
        } catch (Exception ignored) {}

        return seller;
    }

    public SellerEntity getSellerByUserId(Long userId) {
        return sellerRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller profile not found for user ID: " + userId));
    }

    public SellerEntity getSellerById(Long id) {
        return sellerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with ID: " + id));
    }

    public List<SellerEntity> getAllSellers() {
        return sellerRepository.findAll();
    }

    @Transactional
    public SellerEntity approveKyc(Long sellerId, String adminUsername) {
        SellerEntity seller = getSellerById(sellerId);
        seller.setKycStatus(KycStatus.APPROVED);
        seller = sellerRepository.save(seller);

        SellerKycApprovedEvent event = SellerKycApprovedEvent.builder()
                .sellerId(seller.getId())
                .businessName(seller.getBusinessName())
                .approvedBy(adminUsername)
                .timestamp(LocalDateTime.now())
                .build();
        try {
            kafkaTemplate.send("seller.kyc.approved", seller.getId().toString(), event);
        } catch (Exception ignored) {}

        return seller;
    }
}
