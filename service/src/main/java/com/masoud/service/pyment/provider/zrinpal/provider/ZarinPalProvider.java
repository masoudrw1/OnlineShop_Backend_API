package com.masoud.service.pyment.provider.zrinpal.provider;

import com.masoud.dataaccess.entity.pymant.Transaction;
import com.masoud.service.pyment.provider.zrinpal.client.ZarinPalClient;
import com.masoud.service.pyment.provider.zrinpal.client.ZarinPalClientMock;
import com.masoud.service.pyment.provider.zrinpal.request.ZarinPalRequest;
import com.masoud.service.pyment.provider.zrinpal.request.ZarinPalVerifyRequest;
import com.masoud.service.pyment.provider.zrinpal.response.ZarinPalResponse;
import com.masoud.service.pyment.provider.zrinpal.response.ZarinPalVerifyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ZarinPalProvider {
    @Value("${app.payment-gateway.zarinpal.merchant-id}")
    private String merchantId;
    @Value("${app.payment-gateway.zarinpal.callback-url}")
    private String callBackUrl;
    @Value("${app.payment-gateway.zarinpal.ipg-url}")
    private String ipgurl;
   // private final ZarinPalClient client;
    private final ZarinPalClientMock zarinPalClientMock;
@Autowired
    public ZarinPalProvider(ZarinPalClient client, ZarinPalClientMock zarinPalClientMock) {
       // this.client = client;
        this.zarinPalClientMock = zarinPalClientMock;
}

    public String GoToPayment(Transaction trx)
    {

        ZarinPalRequest request=ZarinPalRequest.builder().
                merchant_id(merchantId).
                callback_url(callBackUrl)
                .amount(trx.getAmount().intValue())
                .currency("IRT")
                .description(trx.getDescription())
                .metaData(ZarinPalRequest.MetaData.builder().
                        email(trx.getCustomer()!=null?trx.getCustomer().getEmail():"").
                        mobile(trx.getCustomer()!=null?trx.getCustomer().getPhone():"").
                        order_id(trx.getInvoice()!=null?trx.getInvoice().getId().toString():"")
                        .build())
        .build();

        ZarinPalResponse response=zarinPalClientMock.GoToPayment(request);
        if(response!=null)
        {
            trx.setAuthority(response.getAuthority());
            trx.setCode(response.getCode());
            trx.setResultMessage(response.getMessage());

        }
        assert response != null;
        return ipgurl +response.getAuthority();
    }

    public Transaction verify(Transaction trx)
    {

        ZarinPalVerifyRequest request=ZarinPalVerifyRequest.builder().
                merchant_id(merchantId)
                .amount(trx.getAmount().intValue())
                .authority(trx.getAuthority())
                .build();

        ZarinPalVerifyResponse response=zarinPalClientMock.verifyPayment(request);
        if(response!=null)
        {
            trx.setVerifyCode(response.getCode());
            trx.setVerifyMessage(response.getMessage());
            trx.setCardHash(response.getCard_hash());
            trx.setCardPan(response.getCard_pan());
            trx.setRefId(response.getRefId());

        }
        return trx;

    }
}
