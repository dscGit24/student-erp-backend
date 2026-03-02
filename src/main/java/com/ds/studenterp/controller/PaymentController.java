package com.ds.studenterp.controller;

import com.ds.studenterp.dto.PaymentResponse;
import com.ds.studenterp.entity.Payment;
import com.ds.studenterp.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping("/{studentFeeId}")
    public Payment makePayment(
            @PathVariable Long studentFeeId,
            @RequestBody Payment payment) {

        return service.makePayment(studentFeeId, payment);
    }

    @GetMapping("/history/{studentFeeId}")
    public List<PaymentResponse> getPaymentHistory(
            @PathVariable Long studentFeeId) {

        return service.getPaymentsByStudentFee(studentFeeId)
                .stream().map(PaymentResponse::new)
                .toList();
    }

    @GetMapping("/receipt/{paymentId}")
    public ResponseEntity<byte[]> downloadReceipt(
            @PathVariable Long paymentId) {

        byte[] pdf = service.generateReceipt(paymentId);

        return ResponseEntity.ok()
                .header("Content-Disposition",
                        "attachment; filename=receipt.pdf")
                .header("Content-Type", "application/pdf")
                .body(pdf);
    }
}