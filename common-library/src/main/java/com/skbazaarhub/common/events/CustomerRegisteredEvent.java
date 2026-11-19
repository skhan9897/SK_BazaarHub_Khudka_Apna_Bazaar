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
public class CustomerRegisteredEvent {
    private Long customerId;
    private Long userId;
    private String email;
    private String mobile;
    private String firstName;
    private String lastName;
    private LocalDateTime timestamp;
}
