package com.masoud.dto.user;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class    UserDto {

    private Long id;
    private String username;
    private String phone;
    private String password;
    private String email;
    private LocalDateTime rigesterdate;
    private Boolean enabled=true;
    private Set<RoleDto> roles;
    private CustomerDto customer;


}
