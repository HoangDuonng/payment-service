package com.graduationproject.payment_service.service.impl;

import com.graduationproject.payment_service.dto.request.PaymentRequestDTO;
import com.graduationproject.payment_service.dto.request.UpdatePaymentStatusRequest;
import com.graduationproject.payment_service.dto.response.PaymentResponseDTO;
import com.graduationproject.payment_service.entity.Payment;
import com.graduationproject.payment_service.mapper.PaymentMapper;
import com.graduationproject.payment_service.repository.PaymentRepository;
import com.graduationproject.payment_service.service.PaymentService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public List<PaymentResponseDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponseDTO> getPaymentsByIsDeletedFalse() {
        return paymentRepository.getPaymentsByIsDeletedFalse().stream()
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponseDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + id));
        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional
    public PaymentResponseDTO createPayment(PaymentRequestDTO request) {
        Payment payment = paymentMapper.toEntity(request);
        payment.setDocumentId(UUID.randomUUID());
        return paymentMapper.toResponse(paymentRepository.save(payment));
    }

    @Override
    @Transactional
    public PaymentResponseDTO updatePayment(Long id, PaymentRequestDTO request) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + id));
        Payment updated = paymentMapper.toEntity(request);
        updated.setId(id);
        updated.setDocumentId(payment.getDocumentId());
        updated.setCreatedAt(payment.getCreatedAt());
        updated.setUpdatedAt(java.time.LocalDateTime.now());
        return paymentMapper.toResponse(paymentRepository.save(updated));
    }

    @Override
    @Transactional
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + id));
        payment.setIsDeleted(true);
        paymentRepository.save(payment);
    }

    public List<PaymentResponseDTO> getPaymentsByBookingId(Long bookingId) {
        return paymentRepository.findByBookingId(bookingId).stream()
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
        public PaymentResponseDTO updatePaymentStatus(Long id, UpdatePaymentStatusRequest request) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + id));
        if (request.getStatus() != null) {
            payment.setStatus(request.getStatus());
        }
        if (request.getTransactionStatus() != null) {
            payment.setTransactionStatus(request.getTransactionStatus());
        }
        payment.setUpdatedAt(java.time.LocalDateTime.now());
        return paymentMapper.toResponse(paymentRepository.save(payment));
    }

}
