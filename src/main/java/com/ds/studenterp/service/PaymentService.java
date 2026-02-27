package com.ds.studenterp.service;

import com.ds.studenterp.entity.Payment;
import com.ds.studenterp.entity.StudentFee;
import com.ds.studenterp.repository.PaymentRepository;
import com.ds.studenterp.repository.StudentFeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import java.io.ByteArrayOutputStream;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final StudentFeeRepository studentFeeRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          StudentFeeRepository studentFeeRepository) {
        this.paymentRepository = paymentRepository;
        this.studentFeeRepository = studentFeeRepository;
    }

    @Transactional
    public Payment makePayment(Long studentFeeId, Payment payment) {

        StudentFee fee = studentFeeRepository.findById(studentFeeId)
                .orElseThrow(() ->
                        new RuntimeException("Student fee record not found"));

        // 🔥 Generate Receipt
        long count = paymentRepository.count() + 1;
        String receipt = "RCPT" +
                LocalDate.now().getYear() +
                String.format("%05d", count);

        payment.setReceiptNumber(receipt);
        payment.setPaymentDate(LocalDate.now());
        payment.setStudentFee(fee);

        Payment savedPayment = paymentRepository.save(payment);

        // 🔥 Update StudentFee
        double newPaid = fee.getAmountPaid() + payment.getAmountPaid();
        fee.setAmountPaid(newPaid);

        double balance = fee.getTotalAmount() - newPaid;
        fee.setBalanceAmount(balance);

        if (balance == 0)
            fee.setStatus("PAID");
        else if (newPaid > 0)
            fee.setStatus("PARTIAL");
        else
            fee.setStatus("PENDING");

        studentFeeRepository.save(fee);

        return savedPayment;
    }

    public List<Payment> getPaymentsByStudentFee(Long studentFeeId) {

        StudentFee fee = studentFeeRepository.findById(studentFeeId)
                .orElseThrow(() ->
                        new RuntimeException("Student fee not found"));

        return paymentRepository.findByStudentFee(fee);
    }

    public byte[] generateReceipt(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        String html = buildReceiptHtml(payment);

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, null);
            builder.toStream(os);
            builder.run();

            return os.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }

    private String buildReceiptHtml(Payment payment) {

        return """
        <html>
        <head>
            <style>
                body { font-family: Arial; padding: 20px; }
                .header { font-size: 20px; font-weight: bold; margin-bottom: 20px; }
                .box { border: 1px solid #000; padding: 15px; }
                table { width: 100%; border-collapse: collapse; }
                td { padding: 8px; }
                .label { font-weight: bold; width: 40%; }
            </style>
        </head>
        <body>

            <div class='header'>STUDENT ERP - PAYMENT RECEIPT</div>

            <div class='box'>
                <table>
                    <tr>
                        <td class='label'>Receipt No:</td>
                        <td>%s</td>
                    </tr>
                    <tr>
                        <td class='label'>Student:</td>
                        <td>%s %s</td>
                    </tr>
                    <tr>
                        <td class='label'>Course:</td>
                        <td>%s</td>
                    </tr>
                    <tr>
                        <td class='label'>Amount Paid:</td>
                        <td>₹ %s</td>
                    </tr>
                    <tr>
                        <td class='label'>Payment Mode:</td>
                        <td>%s</td>
                    </tr>
                    <tr>
                        <td class='label'>Payment Date:</td>
                        <td>%s</td>
                    </tr>
                </table>
            </div>

            <p style="margin-top:40px;">Thank you for your payment.</p>

        </body>
        </html>
        """.formatted(
                payment.getReceiptNumber(),
                payment.getStudentFee().getStudent().getFirstName(),
                payment.getStudentFee().getStudent().getLastName(),
                payment.getStudentFee().getFeeStructure().getCourse().getCourseName(),
                payment.getAmountPaid(),
                payment.getPaymentMode(),
                payment.getPaymentDate()
        );
    }
}