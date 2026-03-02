package com.ds.studenterp.dto;

import com.ds.studenterp.entity.StudentFee;
import lombok.Data;

@Data
public class StudentFeeResponse {

    private Long id;

    private Long studentId;
    private String studentName;

    private Long feeStructureId;

    private Double totalAmount;
    private Double amountPaid;
    private Double balanceAmount;
    private String status;

    private Boolean active;

    public StudentFeeResponse(StudentFee sf) {
        this.id = sf.getId();

        if (sf.getStudent() != null) {
            this.studentId = sf.getStudent().getId();
            this.studentName =
                    sf.getStudent().getFirstName() + " " +
                            sf.getStudent().getLastName();
        }

        if (sf.getFeeStructure() != null) {
            this.feeStructureId = sf.getFeeStructure().getId();
        }

        this.totalAmount = sf.getTotalAmount();
        this.amountPaid = sf.getAmountPaid();
        this.balanceAmount = sf.getBalanceAmount();
        this.status = sf.getStatus();
        this.active = sf.getActive();
    }
}