package com.masoud.service.pyment.provider.zrinpal.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ZarinPalRequest {
    private String merchant_id;
    private Integer amount;
    private String currency;
    private String description;
    private String callback_url;
    private MetaData metaData;

    @Getter
    @Setter
    @Builder
    public static class MetaData
    {
        private String mobile;
        private String email;
        private String order_id;
    }
}
