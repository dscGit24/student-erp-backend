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

        // Generate Receipt
        long count = paymentRepository.count() + 1;
        String receipt = "RCPT" +
                LocalDate.now().getYear() +
                String.format("%05d", count);

        payment.setReceiptNumber(receipt);
        payment.setPaymentDate(LocalDate.now());
        payment.setStudentFee(fee);
        payment.setActive(true);

        Payment savedPayment = paymentRepository.save(payment);

        // Update StudentFee
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
            e.printStackTrace();
            throw new RuntimeException("Error generating PDF: " + e.getMessage(), e);
        }
    }

    private String buildReceiptHtml(Payment payment) {

        // Safe getters with null checks
        String receiptNumber = payment.getReceiptNumber() != null ? payment.getReceiptNumber() : "N/A";

        String studentName = "N/A";
        String courseName = "N/A";

        if (payment.getStudentFee() != null) {
            if (payment.getStudentFee().getStudent() != null) {
                String firstName = payment.getStudentFee().getStudent().getFirstName() != null ?
                        payment.getStudentFee().getStudent().getFirstName() : "";
                String lastName = payment.getStudentFee().getStudent().getLastName() != null ?
                        payment.getStudentFee().getStudent().getLastName() : "";
                studentName = (firstName + " " + lastName).trim();
                if (studentName.isEmpty()) studentName = "N/A";
            }

            if (payment.getStudentFee().getFeeStructure() != null &&
                    payment.getStudentFee().getFeeStructure().getCourse() != null) {
                courseName = payment.getStudentFee().getFeeStructure().getCourse().getCourseName() != null ?
                        payment.getStudentFee().getFeeStructure().getCourse().getCourseName() : "N/A";
            }
        }

        Double amountPaid = payment.getAmountPaid() != null ? payment.getAmountPaid() : 0.0;
        String paymentMode = payment.getPaymentMode() != null ? payment.getPaymentMode() : "N/A";
        String paymentDate = payment.getPaymentDate() != null ? payment.getPaymentDate().toString() : LocalDate.now().toString();

        String html = """
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8" />
        <style>
            body { font-family: Arial, sans-serif; padding: 30px; margin: 0; color: #333; }
            .header { font-size: 24px; font-weight: bold; color: #2f5d62; margin-bottom: 30px; border-bottom: 2px solid #2f5d62; padding-bottom: 10px; }
            .box { border: 1px solid #ddd; border-radius: 8px; padding: 20px; background-color: #f9f9f9; }
            table { width: 100%%; border-collapse: collapse; }
            td { padding: 12px; border-bottom: 1px solid #eee; }
            .label { font-weight: bold; width: 40%%; color: #555; }
            .value { color: #000; }
            .amount { font-size: 18px; font-weight: bold; color: #2f5d62; }
            .footer { margin-top: 40px; text-align: center; color: #777; font-size: 14px; border-top: 1px dashed #ddd; padding-top: 20px; }
        </style>
    </head>
    <body>
        <div class='header'>STUDENT ERP - PAYMENT RECEIPT</div>
        <div class='box'>
            <table>
                <tr><td class='label'>Receipt No:</td><td class='value'>%s</td></tr>
                <tr><td class='label'>Student:</td><td class='value'>%s</td></tr>
                <tr><td class='label'>Course:</td><td class='value'>%s</td></tr>
                <tr><td class='label'>Amount Paid:</td><td class='value amount'>₹ %,.2f</td></tr>
                <tr><td class='label'>Payment Mode:</td><td class='value'>%s</td></tr>
                <tr><td class='label'>Payment Date:</td><td class='value'>%s</td></tr>
            </table>
        </div>
        <div class='footer'>
            <p>Thank you for your payment. This is a system generated receipt.</p>
            <p>For any queries, please contact the finance department.</p>
        </div>
    </body>
    </html>
    """.formatted(
                escapeHtml(receiptNumber),
                escapeHtml(studentName),
                escapeHtml(courseName),
                amountPaid,
                escapeHtml(paymentMode),
                escapeHtml(paymentDate)
        );

        return html;
    }

    // Helper method to escape HTML special characters
    private String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}