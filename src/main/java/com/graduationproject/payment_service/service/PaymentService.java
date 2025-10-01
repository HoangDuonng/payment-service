package com.graduationproject.payment_service.service;

import com.graduationproject.payment_service.dto.request.PaymentRequestDTO;
import com.graduationproject.payment_service.dto.request.UpdatePaymentStatusRequest;
import com.graduationproject.payment_service.dto.response.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {
    List<PaymentResponseDTO> getAllPayments();

    PaymentResponseDTO getPaymentById(Long id);

    PaymentResponseDTO createPayment(PaymentRequestDTO request);

    PaymentResponseDTO updatePayment(Long id, PaymentRequestDTO request);

    void deletePayment(Long id);

    List<PaymentResponseDTO> getPaymentsByBookingId(Long bookingId);

    List<PaymentResponseDTO> getPaymentsByIsDeletedFalse();

    PaymentResponseDTO updatePaymentStatus(Long id, UpdatePaymentStatusRequest request);
}
