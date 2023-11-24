package com.laptrinhweb.rest;

import com.laptrinhweb.dto.PaymentMethodDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/payment")
public interface PaymentRest {
    @GetMapping("")
    public ResponseEntity<?> getPaymentMethod();

    @PostMapping("")
    public ResponseEntity<?> createPaymentMethod(@RequestBody PaymentMethodDto Dto);

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<?> deletePaymentAccount(@PathVariable("paymentId") Long paymentId);
 }
