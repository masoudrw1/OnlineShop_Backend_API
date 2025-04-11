package com.masoud.service.pyment.provider.zrinpal.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ZarinPalVerifyResponse {
    private String code;
    private String message;
    private String refId;
    private String card_pan;
    private String card_hash;
    private String fee_type;
    private Integer fee;

}
