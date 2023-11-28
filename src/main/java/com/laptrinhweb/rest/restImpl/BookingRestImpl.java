package com.laptrinhweb.rest.restImpl;

import com.laptrinhweb.dto.BookingDto;
import com.laptrinhweb.dto.ResponseData;
import com.laptrinhweb.rest.BookingRest;
import com.laptrinhweb.service.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookingRestImpl implements BookingRest {
    private final IBookingService bookingService;
    @Override
    public ResponseEntity<?> bookHotel(Long hotelId, BookingDto bookingDto) {
        try {
            return bookingService.bookHotel( hotelId,  bookingDto);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(ResponseData.builder().message(e.getMessage()).build());


        }
    }

    @Override
    public ResponseEntity<?> getAllBookedHotel() {
        try {
            return bookingService.getAllBookedHotel();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(500).body(ResponseData.builder().message("Server occur Problem").build());
    }
}
