package com.laptrinhweb.service.serviceImpl;

import com.laptrinhweb.dto.HotelDto;
import com.laptrinhweb.dto.ResponseData;
import com.laptrinhweb.entity.Farvourite;
import com.laptrinhweb.entity.FarvouriteKey;
import com.laptrinhweb.entity.Hotel;
import com.laptrinhweb.entity.User;
import com.laptrinhweb.repository.FarvouriteRepository;
import com.laptrinhweb.repository.HotelRepository;
import com.laptrinhweb.repository.UserRepository;
import com.laptrinhweb.service.IFavouriteService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class FavouriteServiceImpl implements IFavouriteService {
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final FarvouriteRepository farvouriteRepository;
    private final ModelMapper modelMapper;
    @Override
    public ResponseEntity<?> getAll() {
        var userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        List<Farvourite> favouritedIstance=farvouriteRepository.findHotelByUserEmailAndIsFavourited(userDetails.getUsername(), true);
        var favouriteHotels=favouritedIstance.stream()
                .map(farvourite -> farvourite.getHotel()).toList();
        List<HotelDto> favouritedHotelDtos=new ArrayList<>();
        if(favouriteHotels!=null )
            favouritedHotelDtos= favouriteHotels.stream()
                    .map(hotel -> modelMapper.map(hotel, HotelDto.class))
                    .peek(hotelDto -> hotelDto.setIsFavourited(true)).toList();
        return ResponseEntity.ok(ResponseData.builder()
                        .message("Success")
                        .status(HttpStatus.OK)
                        .data(favouritedHotelDtos
                        )
                .build());
    }

    @Override
    public ResponseEntity<?> favouriteHotel(Long hotelId) {
        var userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        var hotel= hotelRepository.findById(hotelId).orElseThrow();
        if(Objects.isNull(hotel))
            return ResponseEntity.status(400).body(ResponseData
                    .builder()
                            .message("Hotel Id is incorrect.")
                            .status(HttpStatus.BAD_REQUEST)
                    .build());
        var user= userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        var favouritedKey=FarvouriteKey.builder().hotelId(hotelId)
                .userId(user.getId()).build();
        Farvourite favouritedInstance=null;
        if(farvouriteRepository.existsById(favouritedKey)){
             favouritedInstance=farvouriteRepository.findById(favouritedKey).orElseThrow();
            favouritedInstance.setIsFavourited(true);
        }else{
             favouritedInstance= Farvourite.builder()
                    .isFavourited(true)
                    .hotel(hotel)
                    .key(favouritedKey)
                    .dateAt(new Date())
                    .user(user)
                    .build();
        }
        farvouriteRepository.save(favouritedInstance);


        return ResponseEntity.ok(ResponseData.builder()
                .message("Success")
                .status(HttpStatus.OK)
                .build());
    }

    @Override
    public ResponseEntity<?> unfavouriteHotel(Long hotelId) {
        var userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        var hotel= hotelRepository.findById(hotelId).orElseThrow();
        if(Objects.isNull(hotel))
            return ResponseEntity.status(400).body(ResponseData
                    .builder()
                    .message("Hotel Id is incorrect.")
                    .status(HttpStatus.BAD_REQUEST)
                    .build());
        var user= userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        var favouritedKey=FarvouriteKey.builder().hotelId(hotelId)
                .userId(user.getId()).build();
        Farvourite favouritedInstance=null;
        if(farvouriteRepository.existsById(favouritedKey)){
            favouritedInstance=farvouriteRepository.findById(favouritedKey).orElseThrow();
            favouritedInstance.setIsFavourited(false);
        }else{
            return ResponseEntity.status(400).body(ResponseData
                    .builder()
                    .message("Not favourited befor.")
                    .status(HttpStatus.BAD_REQUEST)
                    .build());
        }
        farvouriteRepository.save(favouritedInstance);


        return ResponseEntity.ok(ResponseData.builder()
                .message("Success")
                .status(HttpStatus.OK)
                .build());
    }
}
