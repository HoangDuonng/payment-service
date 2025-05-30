package com.graduationproject.payment_service.repository;

import com.graduationproject.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByDocumentId(UUID documentId);

    List<Payment> findByBookingId(Long bookingId);

    List<Payment> getPaymentsByIsDeletedFalse();

    List<Payment> findByBookingIdAndIsDeletedFalse(Long bookingId);
}
