package com.masoud.dto.order;

import com.masoud.dataaccess.entity.order.Invoice;
import com.masoud.dataaccess.entity.order.InvoiceItem;
import com.masoud.dataaccess.entity.product.Color;
import com.masoud.dataaccess.entity.product.Product;
import com.masoud.dataaccess.entity.product.Size;
import com.masoud.dataaccess.enums.OrderStatus;
import com.masoud.dto.product.ColorDto;
import com.masoud.dto.product.ProductDto;
import com.masoud.dto.product.SizeDto;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class InvoiceItemDto {

    private Long id;
    private ProductDto product;
    private SizeDto size;
    private ColorDto color;
    private Integer quantity;
    private Long price;
}
