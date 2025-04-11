package com.masoud.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassDto {
    String oldPass;
    String newPass;
    String newPass2;
}
