package com.masoud.dataaccess.entity.order;

import com.masoud.dataaccess.entity.files.Files;
import com.masoud.dataaccess.entity.user.User;
import com.masoud.dataaccess.enums.BlogStatus;
import com.masoud.dataaccess.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private LocalDateTime createDate;
    private LocalDateTime payedDate;
    private OrderStatus orderStatus;
    private Long totalAmount;

    @OneToMany(mappedBy = "invoice")

    private List<InvoiceItem> invoiceItems;
    @ManyToOne
    private User user;

}
