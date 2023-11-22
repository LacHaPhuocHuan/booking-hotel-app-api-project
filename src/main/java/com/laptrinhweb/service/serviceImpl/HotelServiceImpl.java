package com.laptrinhweb.service.serviceImpl;

import com.laptrinhweb.dto.HotelDto;
import com.laptrinhweb.dto.ResponseData;
import com.laptrinhweb.entity.Hotel;
import com.laptrinhweb.repository.HotelRepository;
import com.laptrinhweb.repository.ReviewRepository;
import com.laptrinhweb.service.IHotelService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Override
    public ResponseEntity<ResponseData> getAll() {
        var hotels =hotelRepository.findAll();
        return ResponseEntity.ok(
                ResponseData.builder()
                        .status(HttpStatus.OK)
                        .message("Success")
                        .data(hotels.stream().map( hotel -> modelMapper.map(hotel, HotelDto.class)).toList())
                        .build()
        );
    }

    @Override
    public ResponseEntity<ResponseData> getById(Long id) {
        var hotel=hotelRepository.findById(id).orElseThrow();
        if(Objects.isNull(hotel))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseData.builder()
                            .message("Not found.")
                            .status(HttpStatus.OK)
                            .build());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseData.builder()
                        .message("Success")
                        .data(modelMapper.map(hotel, HotelDto.class))
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
                        .data(hotels.stream().map( hotel -> modelMapper.map(hotel, HotelDto.class)).toList())
                        .build()
        );
    }

    @Override
    public ResponseEntity<?> getPopular() {
        List<Object[]> objects= hotelRepository.findHotelsWithMostReviews();

        return ResponseEntity.ok(
                ResponseData.builder()
                        .status(HttpStatus.OK)
                        .message("Success")
                        .data(objects.stream().map( hotel -> {
                            var hotelDto= modelMapper.map(hotel[0], HotelDto.class);
                            hotelDto.setReviewQuantity(Integer.parseInt(hotel[1].toString()));
                            return hotelDto;
                                })
                                .toList())
                        .build()
        );
    }
}
