package com.ds.studenterp.entity;

import com.ds.studenterp.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fee_structures")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeeStructure extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "course_id", unique = true)
    private Course course;

    private Double tuitionFee;
    private Double examFee;
    private Double otherCharges;

    private Double totalAmount;

    @Column(nullable = false)
    private Boolean active = true;

    @PrePersist
    @PreUpdate
    public void calculateTotal() {
        double tuition = tuitionFee != null ? tuitionFee : 0;
        double exam = examFee != null ? examFee : 0;
        double other = otherCharges != null ? otherCharges : 0;

        this.totalAmount = tuition + exam + other;
    }
}