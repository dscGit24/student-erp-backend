package com.ds.studenterp.dto;

import com.ds.studenterp.entity.Payment;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentResponse {

    private Long id;

    private Long studentFeeId;
    private Long studentId;
    private String studentName;

    private Double amountPaid;
    private LocalDate paymentDate;

    private String paymentMode;
    private String transactionId;
    private String receiptNumber;

    private Boolean active;

    public PaymentResponse(Payment payment) {

        this.id = payment.getId();

        if (payment.getStudentFee() != null) {

            this.studentFeeId = payment.getStudentFee().getId();

            if (payment.getStudentFee().getStudent() != null) {
                this.studentId =
                        payment.getStudentFee().getStudent().getId();

                this.studentName =
                        payment.getStudentFee().getStudent().getFirstName()
                                + " "
                                + payment.getStudentFee().getStudent().getLastName();
            }
        }

        this.amountPaid = payment.getAmountPaid();
        this.paymentDate = payment.getPaymentDate();
        this.paymentMode = payment.getPaymentMode();
        this.transactionId = payment.getTransactionId();
        this.receiptNumber = payment.getReceiptNumber();
        this.active = payment.getActive();
    }
}