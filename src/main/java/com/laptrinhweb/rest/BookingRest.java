package com.laptrinhweb.rest;

import com.laptrinhweb.dto.BookingDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//@Api(value = "Hotel API", tags = "Booking Hotel Operations")

@RequestMapping("/api/v1/booking")
public interface BookingRest {
    @PostMapping("/{hotelId}")
//    @ApiOperation(value = "Book a Hotel", notes = "Book a hotel with the provided details.")
    public ResponseEntity<?> bookHotel(@PathVariable("hotelId") Long hotelId, @RequestBody BookingDto bookingDto);

    @GetMapping("")
//    @ApiOperation(value = "Get All Booked Hotels", notes = "Retrieve information about all booked hotels.")
    public  ResponseEntity<?> getAllBookedHotel();
}
