package com.skbazaarhub.common.events;

import com.skbazaarhub.common.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent {
    private String recipient;
    private String subject;
    private String body;
    private NotificationType type;
    private Map<String, String> metadata;
}
