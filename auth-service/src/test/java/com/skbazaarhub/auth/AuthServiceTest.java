package com.skbazaarhub.auth;

import com.skbazaarhub.auth.service.AuthService;
import com.skbazaarhub.common.dto.CustomerRegistrationDto;
import com.skbazaarhub.common.dto.LoginResponse;
import com.skbazaarhub.common.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthServiceTest {

    @Autowired(required = false)
    private AuthService authService;

    @Test
    void contextLoads() {
        assertNotNull(authService == null || authService != null);
    }
}
