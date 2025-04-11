package com.masoud.service.pyment;

import com.masoud.comman.exseptions.NotFoundExceptions;
import com.masoud.comman.exseptions.ValidationExceptions;
import com.masoud.dataaccess.entity.order.Invoice;
import com.masoud.dataaccess.entity.pymant.Payment;
import com.masoud.dataaccess.entity.pymant.Transaction;
import com.masoud.dataaccess.entity.user.User;
import com.masoud.dataaccess.repository.pyment.PaymentRepository;
import com.masoud.dataaccess.repository.pyment.TransactionRepository;
import com.masoud.dto.order.InvoiceDto;
import com.masoud.dto.order.InvoiceItemDto;
import com.masoud.dto.payment.GoToPaymentDto;
import com.masoud.dto.product.ColorDto;
import com.masoud.dto.product.ProductDto;
import com.masoud.dto.product.SizeDto;
import com.masoud.dto.user.CustomerDto;
import com.masoud.dto.user.LimitUserDto;
import com.masoud.dto.user.UserDto;
import com.masoud.service.User.UserService;
import com.masoud.service.order.InvoiceService;
import com.masoud.service.pyment.provider.zrinpal.provider.ZarinPalProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ZarinPalProvider zarinPalProvider;
    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final InvoiceService invoiceService;
    private final ModelMapper mapper;
@Autowired
    public PaymentService(PaymentRepository pymentRepository, ZarinPalProvider zarinPalProvider, TransactionRepository transactionRepository, UserService userService, InvoiceService invoiceService, ModelMapper mapper) {
        this.paymentRepository = pymentRepository;
    this.zarinPalProvider = zarinPalProvider;
    this.transactionRepository = transactionRepository;
    this.userService = userService;
    this.invoiceService = invoiceService;
    this.mapper = mapper;
}
/**
 * Validation
 * creat new user
 * creat new invoice + invoice item
 * calculate total amount
 * creat new transition
 * send request to bank IPG
 * Receive IPG response
 * update transition =>save IPG token
 * redirect user to IPG url
 */
    @Transactional
    public String gotoPayment(GoToPaymentDto dto) throws Exception {
        ChekValidetion(dto);
        UserDto user = userService.create(UserDto.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .phone(dto.getMobile())
                .email(dto.getEmail())
                .customer(CustomerDto.builder()
                        .firstname(dto.getFirstname())
                        .lastname(dto.getLastname())
                        .tel(dto.getTel())
                        .address(dto.getAddress())
                        .postalcode(dto.getPostalcode())
                        .build())
                .build());


        InvoiceDto invoice = invoiceService.create(InvoiceDto.builder()
                        .user(LimitUserDto.builder()
                                .id(user.getId())
                                .build())
                .invoiceItems(dto.getItems().stream().map(x -> InvoiceItemDto.builder()
                        .product(ProductDto.builder().id(x.getProductId()).build())
                        .color(ColorDto.builder().id(x.getColorId()).build())
                        .size(SizeDto.builder().id(x.getSizeId()).build())
                        .quantity(x.getQuantity())
                        .build()).toList())
                .build());

        Payment paymentGatway =paymentRepository.findFirstByNameEqualsIgnoreCase(dto.getGateway().toString()).orElseThrow(NotFoundExceptions::new);
        Transaction trx=Transaction.builder().
                amount(invoice.getTotalAmount())
                .pyment(paymentGatway)
                .description(invoice.getId()+"_"+invoice.getTotalAmount())
                .customer(mapper.map(user, User.class))
                .invoice(mapper.map(invoice, Invoice.class))
                .build();
        String result="";
        switch (dto.getGateway())
        {
            case ZarinPal -> {
                result=zarinPalProvider.GoToPayment(trx);
            }
            case CardToCard -> {
            }
            case TejaratBank -> {
            }
            case PasagadBank -> {
            }
        }
        transactionRepository.save(trx);
        return result;

    }

    public String verify(String authority,String status ) throws NotFoundExceptions {
        if(status==null||status.isEmpty()||status.equalsIgnoreCase("NOK"))
        {
            return "NOK";
        }
        if(status.equalsIgnoreCase("OK"))
        {
            Transaction trx =transactionRepository.findFirstByAuthorityEqualsIgnoreCase(authority).orElseThrow(NotFoundExceptions::new);
            Transaction verifiedTrx =zarinPalProvider.verify(trx);
            transactionRepository.save(verifiedTrx);
            return "OK";

        }
        return "NOK";

    }



    private static void ChekValidetion(GoToPaymentDto dto) throws ValidationExceptions {
        if(dto.getGateway() == null)
        {
            throw new ValidationExceptions("plese select payment gateway");
        }
        if(dto.getFirstname() == null|| dto.getFirstname().isEmpty())
        {
            throw new ValidationExceptions("plese enter first name");
        }
        if(dto.getLastname() == null|| dto.getLastname().isEmpty())
        {
            throw new ValidationExceptions("plese enter last name");
        }
        if(dto.getUsername() == null|| dto.getUsername().isEmpty())
        {
            throw new ValidationExceptions("plese enter user name");
        }
        if(dto.getPassword() == null|| dto.getPassword().isEmpty())
        {
            throw new ValidationExceptions("plese enter password");
        }
        if(dto.getEmail() == null|| dto.getEmail().isEmpty())
        {
            throw new ValidationExceptions("plese enter email");
        }
        if(dto.getMobile() == null|| dto.getMobile().isEmpty())
        {
            throw new ValidationExceptions("plese enter mobile");
        }
        if(dto.getTel() == null|| dto.getTel().isEmpty())
        {
            throw new ValidationExceptions("plese enter telephone");
        }
        if(dto.getAddress() == null|| dto.getAddress().isEmpty())
        {
            throw new ValidationExceptions("plese enter address");
        }
        if(dto.getPostalcode() == null|| dto.getPostalcode().isEmpty())
        {
            throw new ValidationExceptions("plese enter postal code");
        }
        if(dto.getItems() == null|| dto.getItems().isEmpty())
        {
            throw new ValidationExceptions("plese add at list one product to basket");
        }
    }
}
