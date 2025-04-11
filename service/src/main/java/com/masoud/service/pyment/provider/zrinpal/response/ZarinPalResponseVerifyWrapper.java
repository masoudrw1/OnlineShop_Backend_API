package com.masoud.service.pyment.provider.zrinpal.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ZarinPalResponseVerifyWrapper {
    private ZarinPalVerifyResponse data;
    private String[] errors;
}
