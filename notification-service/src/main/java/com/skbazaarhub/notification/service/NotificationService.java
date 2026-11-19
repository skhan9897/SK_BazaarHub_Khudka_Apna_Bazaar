package com.skbazaarhub.notification.service;

import com.skbazaarhub.common.events.NotificationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    @KafkaListener(topics = "notification.requested", groupId = "notification-group")
    public void handleNotificationEvent(NotificationEvent event) {
        log.info("Received notification request of type: {} for recipient: {}", event.getType(), event.getRecipient());

        switch (event.getType()) {
            case SMS:
                sendSms(event.getRecipient(), event.getBody());
                break;
            case EMAIL:
                sendEmail(event.getRecipient(), event.getSubject(), event.getBody());
                break;
            case PUSH_NOTIFICATION:
                sendPushNotification(event.getRecipient(), event.getSubject(), event.getBody());
                break;
            default:
                log.info("In-app notification logged: {}", event.getBody());
        }
    }

    public void sendSms(String mobile, String text) {
        log.info("[MSG91 SMS DISPATCH] To: {}, Text: {}", mobile, text);
    }

    public void sendEmail(String email, String subject, String body) {
        log.info("[AWS SES EMAIL DISPATCH] To: {}, Subject: {}, Body: {}", email, subject, body);
    }

    public void sendPushNotification(String deviceToken, String title, String body) {
        log.info("[FCM PUSH DISPATCH] Token: {}, Title: {}, Body: {}", deviceToken, title, body);
    }
}
