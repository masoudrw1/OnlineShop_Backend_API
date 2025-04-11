package com.masoud.service.pyment.provider.zrinpal.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ZarinPalVerifyRequest {
    private String merchant_id;
    private Integer amount;
    private String authority;

}
