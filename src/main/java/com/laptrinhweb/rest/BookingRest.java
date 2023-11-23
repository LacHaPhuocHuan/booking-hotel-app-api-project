package com.laptrinhweb.rest;

import com.laptrinhweb.dto.BookingDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/booking")
public interface BookingRest {
    @PostMapping("/{hotelId}")
    public ResponseEntity<?> bookHotel(@PathVariable("hotelId") Long hotelId, @RequestBody BookingDto bookingDto);

    @GetMapping("")
    public  ResponseEntity<?> getAllBookedHotel();
}
