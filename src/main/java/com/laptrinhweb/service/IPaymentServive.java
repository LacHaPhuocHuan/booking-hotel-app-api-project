package com.laptrinhweb.service;

import com.laptrinhweb.dto.PaymentMethodDto;
import org.springframework.http.ResponseEntity;

public interface IPaymentServive {
    ResponseEntity<?> createPaymentMethod(PaymentMethodDto dto);

    ResponseEntity<?> getAll();

    ResponseEntity<?> deletePayment(Long paymentId);
}
