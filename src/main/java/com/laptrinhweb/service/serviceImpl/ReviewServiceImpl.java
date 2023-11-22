package com.laptrinhweb.service.serviceImpl;

import com.laptrinhweb.dto.HotelDto;
import com.laptrinhweb.dto.ResponseData;
import com.laptrinhweb.dto.ReviewDto;
import com.laptrinhweb.dto.ReviewResponse;
import com.laptrinhweb.entity.Hotel;
import com.laptrinhweb.entity.Review;
import com.laptrinhweb.repository.HotelRepository;
import com.laptrinhweb.repository.ReviewRepository;
import com.laptrinhweb.repository.UserRepository;
import com.laptrinhweb.service.IReviewService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements IReviewService {
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<?> getReviewsByHotelId(Long hotelId) {
        List<Review> reviewList=reviewRepository.findByHotelId(hotelId);
        return ResponseEntity.ok(
                ResponseData.builder()
                        .status(HttpStatus.OK)
                        .message("Success")
                        .data(reviewList.stream().map(review -> modelMapper.map(review, ReviewResponse.class)).toList())
                        .build()
        );
    }

    @Override
    public ResponseEntity<?> getRate(Long hotelId) {
        List<Review> reviewList=reviewRepository.findByHotelId(hotelId);
        double totalRate=0;
        for (Review review: reviewList
             ) {
            totalRate=totalRate+review.getRate();
        }
        double rate=totalRate/reviewList.size();
        return ResponseEntity.ok(
                ResponseData.builder()
                        .status(HttpStatus.OK)
                        .message("Success")
                        .data( rate)
                        .build()
        );
    }

    @Override
    public ResponseEntity<?> evalueHotel(Long hotelId, ReviewDto reviewDto) {
        var hotel=hotelRepository.findById(hotelId).orElseThrow();
        if(reviewDto.rate()>5 || reviewDto.rate()<0 || Objects.isNull(hotel)) {
            return ResponseEntity.status(400).body(
                    ResponseData.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Fail")
                    .build());
        }
        var review= Review.builder()
                .content(reviewDto.content())
                .rate(reviewDto.rate()).build();

        var user= (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        review.setUser(userRepository.findByEmail(user.getUsername()).orElseThrow(()->new UsernameNotFoundException("")));
        review.setHotel(hotel);
        reviewRepository.save(review);
        return ResponseEntity.ok(
                ResponseData.builder()
                        .status(HttpStatus.OK)
                        .message("Success")
                        .data(modelMapper.map(review, ReviewResponse.class))
                        .build()
        );
    }


}
