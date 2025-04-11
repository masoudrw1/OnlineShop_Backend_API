package com.masoud.dataaccess.entity.pymant;

import com.masoud.dataaccess.entity.order.Invoice;
import com.masoud.dataaccess.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TRX")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long amount;
    @ManyToOne
    private Invoice invoice;
    @ManyToOne
    private User customer;
    @ManyToOne
    private Payment pyment;
    private String authority;
    private String code;
    private String verifyCode;
    private String description;
    private String resultMessage;
    private String verifyMessage;
    private String cardHash;
    private String cardPan;
    private String refId;
}
