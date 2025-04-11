package com.masoud.dto.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.masoud.enums.PaymentGateway;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoToPaymentDto {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private String mobile;
    private String tel;
    private String address;
    private String postalcode;

    private PaymentGateway gateway;
    private List<BasketItem> items;
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BasketItem
    {
        private Long productId;
        private Integer quantity;
        private Long colorId;
        private Long sizeId;

    }
}
