package com.laptrinhweb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbPaymentAccount")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String cardName;
    private String carNumber;
    private String CVV;
    private String memberSince;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;



}
