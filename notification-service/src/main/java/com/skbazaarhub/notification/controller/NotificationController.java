package com.skbazaarhub.notification.controller;

import com.skbazaarhub.common.dto.ApiResponse;
import com.skbazaarhub.common.events.NotificationEvent;
import com.skbazaarhub.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<String>> sendNotification(@RequestBody NotificationEvent event) {
        notificationService.handleNotificationEvent(event);
        return ResponseEntity.ok(ApiResponse.success("Notification processed successfully"));
    }
}
