package com.laptrinhweb.rest.restImpl;

import com.laptrinhweb.dto.ResponseData;
import com.laptrinhweb.dto.ReviewDto;
import com.laptrinhweb.rest.ReviewRest;
import com.laptrinhweb.service.IReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ReviewRestImpl implements ReviewRest {

    private final IReviewService reviewService;
    @Override
    public ResponseEntity<?> getReviewsByHotelId(Long hotelId) {
        try {
            return reviewService.getReviewsByHotelId(hotelId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(500).body(ResponseData.builder().message("Server occur Problem").build());
    }

    @Override
    public ResponseEntity<?> getRate(Long hotelId) {
        try {
            return reviewService.getRate(hotelId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(500).body(ResponseData.builder().message("Server occur Problem").build());
    }

    @Override
    public ResponseEntity<?> evaluate(Long hotelId, ReviewDto reviewDto) {
        try {
            return reviewService.evalueHotel(hotelId,reviewDto);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(500).body(ResponseData.builder().message("Server occur Problem").build());
    }
}
