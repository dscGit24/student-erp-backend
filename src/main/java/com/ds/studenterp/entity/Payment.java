package com.ds.studenterp.entity;

import com.ds.studenterp.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_fee_id")
    private StudentFee studentFee;

    private Double amountPaid;

    private LocalDate paymentDate;

    private String paymentMode;
    // CASH | UPI | CARD | BANK

    private String transactionId;

    @Column(unique = true)
    private String receiptNumber;

    @Column(nullable = false)
    private Boolean active = true;
}