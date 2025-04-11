 package com.masoud.dataaccess.entity.order;

import com.masoud.dataaccess.entity.product.Color;
import com.masoud.dataaccess.entity.product.Product;
import com.masoud.dataaccess.entity.product.Size;
import com.masoud.dataaccess.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class    InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Invoice invoice;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Size size;

    @ManyToOne
    private Color color;

    private Integer quantity;
    private Long price;

}
