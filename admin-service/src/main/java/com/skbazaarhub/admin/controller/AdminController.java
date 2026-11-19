package com.skbazaarhub.admin.controller;

import com.skbazaarhub.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    @GetMapping("/analytics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboardAnalytics() {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("totalSales", 452800.00);
        metrics.put("totalOrders", 1240);
        metrics.put("activeSellers", 85);
        metrics.put("registeredCustomers", 12400);
        metrics.put("pendingKycApprovals", 3);

        return ResponseEntity.ok(ApiResponse.success(metrics, "Analytics fetched successfully"));
    }
}
