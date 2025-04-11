package com.masoud.app.conroller.open;

import com.masoud.app.model.APIResponse;
import com.masoud.app.model.enums.APIStatus;
import com.masoud.comman.exseptions.ValidationExceptions;
import com.masoud.dto.payment.GoToPaymentDto;
import com.masoud.service.pyment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/payment")

public class PaymentController {
    private final PaymentService paymentService;
@Autowired
    public PaymentController(PaymentService pymentService) {
        this.paymentService = pymentService;
    }
    @Transactional
    @PostMapping("goToPayment")
    public APIResponse<String> GoToPayment(@RequestBody GoToPaymentDto dto) {
        try {
            return APIResponse.<String>builder().data(paymentService.gotoPayment(dto)).
                    status(APIStatus.OK).build();
        } catch (ValidationExceptions e) {
            return APIResponse.<String>builder().message(e.getMessage()).
                    status(APIStatus.ERROR).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
