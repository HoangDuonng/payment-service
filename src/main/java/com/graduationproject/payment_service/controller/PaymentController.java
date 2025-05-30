package com.graduationproject.payment_service.controller;

import com.graduationproject.payment_service.dto.request.PaymentRequestDTO;
import com.graduationproject.payment_service.dto.request.UpdatePaymentStatusRequest;
import com.graduationproject.payment_service.dto.response.PaymentResponseDTO;
import com.graduationproject.payment_service.service.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public List<PaymentResponseDTO> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public PaymentResponseDTO getPaymentById(@PathVariable Long id) {
        try {
            return paymentService.getPaymentById(id);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @GetMapping("/available")
    public List<PaymentResponseDTO> getPaymentsByIsDeletedFalse() {
        return paymentService.getPaymentsByIsDeletedFalse();
    }

    @PostMapping
    public PaymentResponseDTO createPayment(@RequestBody PaymentRequestDTO request) {
        return paymentService.createPayment(request);
    }

    @PutMapping("/{id}")
    public PaymentResponseDTO updatePayment(@PathVariable Long id, @RequestBody PaymentRequestDTO request) {
        try {
            return paymentService.updatePayment(id, request);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        try {
            paymentService.deletePayment(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @GetMapping("/booking/{bookingId}")
    public List<PaymentResponseDTO> getPaymentsByBookingId(@PathVariable Long bookingId) {
        return paymentService.getPaymentsByBookingId(bookingId);
    }

    @PatchMapping("/{id}/status")
    public PaymentResponseDTO updatePaymentStatus(
        @PathVariable Long id,
        @RequestBody UpdatePaymentStatusRequest request) {
    return paymentService.updatePaymentStatus(id, request);
    }
}
