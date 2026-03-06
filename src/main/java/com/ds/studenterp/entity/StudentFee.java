package com.ds.studenterp.entity;

import com.ds.studenterp.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student_fees")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class StudentFee extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "student_id", unique = true)
    private Student student;

    @ManyToOne
    private FeeStructure feeStructure;

    private Double totalAmount;
    private Double amountPaid = 0.0;
    private Double balanceAmount;

    private String status;
    // PENDING | PARTIAL | PAID

    @Column(nullable = false)
    private Boolean active = true;
}