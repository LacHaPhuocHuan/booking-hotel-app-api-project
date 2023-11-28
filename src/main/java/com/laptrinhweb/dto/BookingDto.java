package com.laptrinhweb.dto;

import com.laptrinhweb.entity.PaymentMethod;
import com.laptrinhweb.entity.Room;
import com.laptrinhweb.entity.RoomType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor@Builder
public class BookingDto {
    private Long id;
    private Date startDate;
    private Date endDate;
    private int adultsQuantity;
    private int childQuantity;
    private String phoneNumber;
    private PaymentMethod paymentMethod;
    private List<RoomType> roomTypes;
    private Double hotelRate;
    private int reviewQuantity;
    private int hotelId;

}
