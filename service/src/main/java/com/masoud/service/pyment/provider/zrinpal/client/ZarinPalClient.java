package com.masoud.service.pyment.provider.zrinpal.client;

import com.masoud.service.pyment.provider.zrinpal.request.ZarinPalRequest;
import com.masoud.service.pyment.provider.zrinpal.request.ZarinPalVerifyRequest;
import com.masoud.service.pyment.provider.zrinpal.response.ZarinPalResponse;
import com.masoud.service.pyment.provider.zrinpal.response.ZarinPalResponseVerifyWrapper;
import com.masoud.service.pyment.provider.zrinpal.response.ZarinPalResponseWrapper;
import com.masoud.service.pyment.provider.zrinpal.response.ZarinPalVerifyResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
public class ZarinPalClient {
    @Value("${app.payment-gateway.zarinpal.base-url}")
    private String baseUrl;

    public ZarinPalResponse GoToPayment(ZarinPalRequest request)
    {
        String url = baseUrl + "v4/payment/request.json";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);// ارسال دیتا به صورت json
        HttpEntity<ZarinPalRequest> requestHttpEntity=new HttpEntity<>(request,headers);

       RestTemplate restTemplate = new RestTemplate();
       ResponseEntity<ZarinPalResponseWrapper> response =restTemplate.postForEntity(url,requestHttpEntity,ZarinPalResponseWrapper.class);
       return Objects.requireNonNull(response.getBody()).getData();
    }
    public ZarinPalVerifyResponse verifyPayment(ZarinPalVerifyRequest request)
    {
        String url = baseUrl + "v4/payment/verify.json";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);// ارسال دیتا به صورت json
        HttpEntity<ZarinPalVerifyRequest> requestHttpEntity=new HttpEntity<>(request,headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ZarinPalResponseVerifyWrapper> response =restTemplate.postForEntity(url,requestHttpEntity, ZarinPalResponseVerifyWrapper.class);
        return Objects.requireNonNull(response.getBody()).getData();

        //ZarinPalResponseVerifyWrapper برای داشتن متغیر خطا و دیتا ساخته شده است
    }
}
