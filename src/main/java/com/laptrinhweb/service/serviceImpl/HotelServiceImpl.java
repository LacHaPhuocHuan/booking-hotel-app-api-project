package com.laptrinhweb.service.serviceImpl;

import com.laptrinhweb.dto.HotelDto;
import com.laptrinhweb.dto.ResponseData;
import com.laptrinhweb.entity.Farvourite;
import com.laptrinhweb.entity.FarvouriteKey;
import com.laptrinhweb.entity.Hotel;
import com.laptrinhweb.entity.Review;
import com.laptrinhweb.repository.FarvouriteRepository;
import com.laptrinhweb.repository.HotelRepository;
import com.laptrinhweb.repository.ReviewRepository;
import com.laptrinhweb.repository.UserRepository;
import com.laptrinhweb.service.IHotelService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class HotelServiceImpl implements IHotelService {
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final ReviewRepository reviewRepository;
    private final FarvouriteRepository farvouriteRepository;
    private final UserRepository userRepository;
    @Override
    public ResponseEntity<ResponseData> getAll() {
        var hotels =hotelRepository.findAll();
        UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        var user=userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        return ResponseEntity.ok(
                ResponseData.builder()
                        .status(HttpStatus.OK)
                        .message("Success")
                        .data(hotels.stream()
                                .map( hotel -> modelMapper.map(hotel, HotelDto.class))
                                .peek(hotelDto -> {
                                    List<Review> reviewList=reviewRepository.findByHotelId(hotelDto.getId());
                                    double totalRate=0;
                                    for (Review review: reviewList
                                    ) {
                                        totalRate=totalRate+review.getRate();
                                    }
                                    double rate=totalRate/reviewList.size();
                                    hotelDto.setRate(rate);
                                    hotelDto.setReviewQuantity(reviewList.size());
                                    //favourite
                                    FarvouriteKey key=FarvouriteKey.builder()
                                            .hotelId(hotelDto.getId())
                                            .userId(user.getId())
                                            .build();
                                    Farvourite favourite= null;
                                    try{
                                        favourite=farvouriteRepository.findById(key).orElseThrow();
                                    }catch (Exception ex){
                                        log.error(ex.getMessage());
                                    }
                                    if(favourite!=null && favourite.getIsFavourited()==true)
                                        hotelDto.setIsFavourited(true);
                                    else hotelDto.setIsFavourited(false);




                                })
                                .toList())
                        .build()
        );
    }

    @Override
    public ResponseEntity<ResponseData> getById(Long id) {
        var hotel=hotelRepository.findById(id).orElseThrow();
        UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        var user=userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        if(Objects.isNull(hotel))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseData.builder()
                            .message("Not found.")
                            .status(HttpStatus.OK)
                            .build());
        var hotelDto=modelMapper.map(hotel, HotelDto.class);
        List<Review> reviewList=reviewRepository.findByHotelId(hotelDto.getId());
        double totalRate=0;
        for (Review review: reviewList
        ) {
            totalRate=totalRate+review.getRate();
        }
        double rate=totalRate/reviewList.size();
        hotelDto.setRate(rate);
        hotelDto.setReviewQuantity(reviewList.size());
        FarvouriteKey key=FarvouriteKey.builder()
                .hotelId(hotelDto.getId())
                .userId(user.getId())
                .build();
        Farvourite favourite= null;
        try{
            favourite=farvouriteRepository.findById(key).orElseThrow();
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        if(favourite!=null && favourite.getIsFavourited()==true)
            hotelDto.setIsFavourited(true);
        else             hotelDto.setIsFavourited(false);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseData.builder()
                        .message("Success")
                        .data(hotelDto)
                        .status(HttpStatus.OK)
                        .build());
    }

    @Override
    public ResponseEntity<?> findByKeyword(String keyword) {
        Double searchPrice=0.0;
        try{
            searchPrice=Double.parseDouble(keyword);
        }catch (Exception e){
            log.error(e.getMessage());
        }

        List<Hotel> hotels = hotelRepository.findByNameContainsOrAddressContainsOrOverviewContainsOrPriceEquals(keyword, keyword, keyword,searchPrice);
        return ResponseEntity.ok(
                ResponseData.builder()
                        .status(HttpStatus.OK)
                        .message("Success")
                        .data(hotels.stream()
                                .map( hotel -> modelMapper.map(hotel, HotelDto.class))
                                .peek(hotelDto -> {
                                    List<Review> reviewList=reviewRepository.findByHotelId(hotelDto.getId());
                                    double totalRate=0;
                                    for (Review review: reviewList
                                    ) {
                                        totalRate=totalRate+review.getRate();
                                    }
                                    double rate=totalRate/reviewList.size();
                                    hotelDto.setRate(rate);
                                    hotelDto.setReviewQuantity(reviewList.size());
                                })
                                .toList())
                        .build()
        );
    }

    @Override
    public ResponseEntity<?> getPopular() {
        List<Object[]> objects= hotelRepository.findHotelsWithMostReviews();
        UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        List<Farvourite> favouritedInstanse=farvouriteRepository.findHotelByUserEmailAndIsFavourited(userDetails.getUsername(), true);
        List<Hotel> farvouriteHotel=favouritedInstanse.stream().map(farvourite -> farvourite.getHotel()).toList();
        var hotels=objects.stream().map( ob -> {
            var ht= modelMapper.map(ob[0], Hotel.class); var hotelDto= modelMapper.map(ob[0], HotelDto.class);
            hotelDto.setReviewQuantity(Integer.parseInt(ob[1].toString()));
            if(farvouriteHotel.contains(ht))
                hotelDto.setIsFavourited(true);
            else  hotelDto.setIsFavourited(false);
            return hotelDto;

        }) .peek(hotelDto -> {
                    List<Review> reviewList=reviewRepository.findByHotelId(hotelDto.getId());
                    double totalRate=0;
                    for (Review review: reviewList
                    ) {
                        totalRate=totalRate+review.getRate();
                    }
                    double rate=totalRate/reviewList.size();
                    hotelDto.setRate(rate);
                    hotelDto.setReviewQuantity(reviewList.size());
                })
                .toList();

        return ResponseEntity.ok(
                ResponseData.builder()
                        .status(HttpStatus.OK)
                        .message("Success")
                        .data(hotels)
                        .build()
        );
    }
}
