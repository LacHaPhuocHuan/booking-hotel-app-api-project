package com.laptrinhweb.service;

import com.laptrinhweb.dto.ReviewDto;
import org.springframework.http.ResponseEntity;

public interface IReviewService {
    ResponseEntity<?> getReviewsByHotelId(Long hotelId);

    ResponseEntity<?> getRate(Long hotelId);


    ResponseEntity<?> evalueHotel(Long hotelId, ReviewDto reviewDto);
}
