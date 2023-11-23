package com.laptrinhweb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.Map;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbBookingDetail")
public class BookingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date startDate;
    private Date endDate;
    private int adultsQuantity;
    private int childQuantity;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private List<Long>rooms;
    @ManyToOne
    private User user;
}
