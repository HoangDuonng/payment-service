package com.graduationproject.payment_service.dto.request;

import com.graduationproject.payment_service.entity.PaymentStatus;
import com.graduationproject.payment_service.entity.TransactionStatus;
import lombok.Data;

@Data
public class UpdatePaymentStatusRequest {
    private PaymentStatus status;
    private TransactionStatus transactionStatus;
}
