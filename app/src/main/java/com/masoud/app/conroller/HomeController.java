package com.masoud.app.conroller;

import com.masoud.app.model.APIResponse;
import com.masoud.app.model.enums.APIStatus;
import com.masoud.comman.exseptions.NotFoundExceptions;
import com.masoud.service.pyment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class HomeController {

    @Value("${app.payment-gateway.zarinpal.callback-url}")
    private String callBackUrl;
    private final PaymentService paymentService;
@Autowired
    public HomeController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("verify")
    public APIResponse<String> verify(@RequestParam String Authority,@RequestParam String status)
    {
        try {
            return APIResponse.<String>builder()
                    .status(APIStatus.OK).
                    data(paymentService.verify(Authority,status)).build();
        } catch (NotFoundExceptions e) {
           return APIResponse.<String>builder().status(APIStatus.ERROR).
                   message(e.getMessage()).build();

        }
    }

    @GetMapping("pg/StartPay/{Authority}")
    public APIResponse<String> startPay(@PathVariable String Authority)
    {
        return APIResponse.<String>builder()
                .data(callBackUrl +"?Authority=" +Authority  +"&Status=OK")
                .status(APIStatus.OK)
                .build();

    }
}
