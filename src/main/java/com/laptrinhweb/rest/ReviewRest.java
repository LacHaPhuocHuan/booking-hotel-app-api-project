package com.laptrinhweb.rest;

import com.laptrinhweb.dto.ReviewDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/review")
public interface ReviewRest {
    @GetMapping("/{hotelId}")
    public ResponseEntity<?> getReviewsByHotelId(@PathVariable("hotelId") Long hotelId);

    @GetMapping("/{hotelId}/rate")
    public ResponseEntity<?> getRate(@PathVariable("hotelId") Long hotelId);

    @PostMapping("/{hotelId}")
    public ResponseEntity<?> evaluate(@PathVariable("hotelId") Long hotelId, @RequestBody ReviewDto reviewDto);




}
