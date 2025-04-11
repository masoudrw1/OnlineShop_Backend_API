package com.masoud.service.pyment.provider.zrinpal.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ZarinPalResponse {
    private String code;
    private String message;
    private String authority;
    private String fee_type;
    private Integer fee;

}
