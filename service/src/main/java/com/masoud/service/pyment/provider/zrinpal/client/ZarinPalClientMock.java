package com.masoud.service.pyment.provider.zrinpal.client;

import com.masoud.service.pyment.provider.zrinpal.request.ZarinPalRequest;
import com.masoud.service.pyment.provider.zrinpal.request.ZarinPalVerifyRequest;
import com.masoud.service.pyment.provider.zrinpal.response.ZarinPalResponse;
import com.masoud.service.pyment.provider.zrinpal.response.ZarinPalVerifyResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Component
public class ZarinPalClientMock {
    @Value("${app.payment-gateway.zarinpal.base-url}")
    private String baseUrl;

    public ZarinPalResponse GoToPayment(ZarinPalRequest request) {
        return ZarinPalResponse.builder()
                .authority("MOCK_DATA_AUTHORITY_"+new Random().nextInt(100000,999999))
                .code("100")
                .message("MOCK_DATA")
                .build();

    }
    public ZarinPalVerifyResponse verifyPayment(ZarinPalVerifyRequest request) {
        return ZarinPalVerifyResponse.builder()
                .code("100")
                .message("VERIFIED")
                .card_hash("FAKLIJSVDKNKND545451DFKJBKJSBDNBHKKBCNKSDC4DCSDC")
                .card_pan("5022229********5995")
                .refId("201")
                .fee_type("Merchand")
                .fee(0)
                .build();

    }
}