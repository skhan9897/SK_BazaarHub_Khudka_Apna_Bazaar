package com.skbazaarhub.common.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServers;

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        configProps.put(ProducerConfig.RETRIES_CONFIG, 3);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<String, Object> template) {
        return new DefaultErrorHandler(
                new DeadLetterPublishingRecoverer(template),
                new FixedBackOff(1000L, 3L)
        );
    }

    // 15 KAFKA BUSINESS TOPICS
    @Bean
    public NewTopic userRegisteredTopic() {
        return TopicBuilder.name("user.registered").partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic userEmailOtpRequestedTopic() {
        return TopicBuilder.name("user.email.otp.requested").partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic userEmailOtpVerifiedTopic() {
        return TopicBuilder.name("user.email.otp.verified").partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic userMobileOtpRequestedTopic() {
        return TopicBuilder.name("user.mobile.otp.requested").partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic userMobileOtpVerifiedTopic() {
        return TopicBuilder.name("user.mobile.otp.verified").partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic sellerRegisteredTopic() {
        return TopicBuilder.name("seller.registered").partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic orderCreatedTopic() {
        return TopicBuilder.name("order.created").partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic orderConfirmedTopic() {
        return TopicBuilder.name("order.confirmed").partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic orderCancelledTopic() {
        return TopicBuilder.name("order.cancelled").partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic paymentInitiatedTopic() {
        return TopicBuilder.name("payment.initiated").partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic paymentSuccessfulTopic() {
        return TopicBuilder.name("payment.successful").partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic paymentFailedTopic() {
        return TopicBuilder.name("payment.failed").partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic inventoryUpdatedTopic() {
        return TopicBuilder.name("inventory.updated").partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic notificationRequestedTopic() {
        return TopicBuilder.name("notification.requested").partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic orderStatusUpdatedTopic() {
        return TopicBuilder.name("order.status.updated").partitions(3).replicas(1).build();
    }
}
