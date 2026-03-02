package com.ds.studenterp.dto;

import com.ds.studenterp.entity.FeeStructure;
import lombok.Data;

@Data
public class FeeStructureResponse {

    private Long id;

    private Long courseId;
    private String courseName;

    private Double tuitionFee;
    private Double examFee;
    private Double otherCharges;
    private Double totalAmount;

    private Boolean active;

    public FeeStructureResponse(FeeStructure fee) {
        this.id = fee.getId();

        if (fee.getCourse() != null) {
            this.courseId = fee.getCourse().getId();
            this.courseName = fee.getCourse().getCourseName();
        }

        this.tuitionFee = fee.getTuitionFee();
        this.examFee = fee.getExamFee();
        this.otherCharges = fee.getOtherCharges();
        this.totalAmount = fee.getTotalAmount();
        this.active = fee.getActive();
    }
}