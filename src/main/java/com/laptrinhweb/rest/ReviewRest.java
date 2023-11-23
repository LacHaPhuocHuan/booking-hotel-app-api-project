package com.laptrinhweb.rest;

import com.laptrinhweb.dto.ReviewDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/review")
//@Api(value = "Hotel API", tags = "Review Hotel Operations")

public interface ReviewRest {
    @GetMapping("/{hotelId}")
//    @ApiOperation(value = "Get Reviews by Hotel ID", notes = "Retrieve reviews for a specific hotel.")
    public ResponseEntity<?> getReviewsByHotelId(@PathVariable("hotelId") Long hotelId);

    @GetMapping("/{hotelId}/rate")
//    @ApiOperation(value = "Get Rate for a Hotel", notes = "Retrieve the rate for a specific hotel.")

    public ResponseEntity<?> getRate(@PathVariable("hotelId") Long hotelId);

    @PostMapping("/{hotelId}")
//    @ApiOperation(value = "Evaluate a Hotel", notes = "Submit a review for a specific hotel.")
    public ResponseEntity<?> evaluate(@PathVariable("hotelId") Long hotelId, @RequestBody ReviewDto reviewDto);




}
