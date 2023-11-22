package com.laptrinhweb.service;

import com.laptrinhweb.dto.BookingDto;
import org.springframework.http.ResponseEntity;

public interface IBookingService {
    ResponseEntity<?> bookHotel(Long hotelId, BookingDto bookingDto);

    ResponseEntity<?> getAllBookedHotel();
}
