package com.masoud.service.pyment.provider.zrinpal.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ZarinPalResponseWrapper {
    private ZarinPalResponse data;
    private String[] errors;
}
