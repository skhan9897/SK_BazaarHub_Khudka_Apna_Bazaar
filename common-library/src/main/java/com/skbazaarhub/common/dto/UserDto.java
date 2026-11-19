package com.skbazaarhub.common.dto;

import com.skbazaarhub.common.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String mobile;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private boolean verified;
    private Set<RoleName> roles;
}
