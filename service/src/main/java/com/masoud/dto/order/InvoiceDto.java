package com.masoud.dto.order;

import com.masoud.dataaccess.entity.order.InvoiceItem;
import com.masoud.dataaccess.enums.OrderStatus;
import com.masoud.dto.user.LimitUserDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class InvoiceDto {

    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime payedDate;
    private OrderStatus orderStatus;
    private Long totalAmount;
    private List<InvoiceItemDto> invoiceItems;
    private LimitUserDto user;

}
