package com.masoud.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LimitUserDto {

    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String token;



}
