package com.graduationproject.payment_service.dto.response;

import com.graduationproject.payment_service.entity.PaymentMethod;
import com.graduationproject.payment_service.entity.PaymentStatus;
import com.graduationproject.payment_service.entity.TransactionStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Map;

@Data
@Builder
public class PaymentResponseDTO {
    private Long id;
    private UUID documentId;
    private Long bookingId;
    private BigDecimal amount;
    private String currency;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private TransactionStatus transactionStatus;
    private String transactionCode;
    private Map<String, Object> paymentGatewayResponse;
    private LocalDateTime paidAt;
    private Boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
