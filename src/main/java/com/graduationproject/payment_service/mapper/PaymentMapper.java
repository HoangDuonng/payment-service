package com.graduationproject.payment_service.mapper;

import com.graduationproject.payment_service.dto.request.PaymentRequestDTO;
import com.graduationproject.payment_service.dto.response.PaymentResponseDTO;
import com.graduationproject.payment_service.entity.Payment;
import com.graduationproject.payment_service.entity.PaymentMethod;
import com.graduationproject.payment_service.entity.PaymentStatus;
import com.graduationproject.payment_service.entity.TransactionStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class PaymentMapper implements BaseMapper<Payment, PaymentResponseDTO> {
    public PaymentResponseDTO toDto(Payment payment) {
        if (payment == null) {
            return null;
        }
        return PaymentResponseDTO.builder()
                .id(payment.getId())
                .documentId(payment.getDocumentId())
                .bookingId(payment.getBookingId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .paymentMethod(payment.getPaymentMethod())
                .status(payment.getStatus())
                .transactionStatus(payment.getTransactionStatus())
                .transactionCode(payment.getTransactionCode())
                .paymentGatewayResponse(payment.getPaymentGatewayResponse())
                .paidAt(payment.getPaidAt())
                .isDeleted(payment.getIsDeleted())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }

    public Payment toEntity(PaymentRequestDTO request) {
        if (request == null) {
            return null;
        }
        return Payment.builder()
                .documentId(UUID.randomUUID())
                .bookingId(request.getBookingId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .paymentMethod(request.getPaymentMethod() != null ? request.getPaymentMethod() : PaymentMethod.OTHER)
                .status(request.getStatus() != null ? request.getStatus() : PaymentStatus.PENDING)
                .transactionStatus(request.getTransactionStatus() != null ? request.getTransactionStatus()
                        : TransactionStatus.PENDING)
                .transactionCode(request.getTransactionCode())
                .paymentGatewayResponse(request.getPaymentGatewayResponse())
                .paidAt(request.getPaidAt())
                .isDeleted(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public PaymentResponseDTO toResponse(Payment payment) {
        return toDto(payment);
    }
}
