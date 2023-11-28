package com.laptrinhweb.service.serviceImpl;

import com.laptrinhweb.dto.BookingDto;
import com.laptrinhweb.dto.HotelDto;
import com.laptrinhweb.dto.ResponseData;
import com.laptrinhweb.entity.*;
import com.laptrinhweb.repository.*;
import com.laptrinhweb.service.IBookingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements IBookingService{
    private final BookingDetailRepository bookingDetailRepository;
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public ResponseEntity<?> bookHotel(Long hotelId, BookingDto bookingDto) {
        UserDetails user=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ;
        User u=userRepository.findByEmail(user.getUsername()).orElseThrow();
        var booking=modelMapper.map(bookingDto, BookingDetail.class);
        var rooms=roomRepository.findByHotelId(hotelId);
        var hotel=hotelRepository.findById(hotelId).orElseThrow();
        List<Room> lentRoom=new ArrayList<>();
        List<RoomType> roomTypes=bookingDto.getRoomTypes();
        for (Room room: rooms
             ) {
            if(roomTypes.contains(room.getRoomType())
                    && room.getRoomStatus().equals(RoomStatus.VACANT)
            ){
                lentRoom.add(room);
                room.setRoomStatus(RoomStatus.OCCUPIED);
                roomTypes.remove(room.getRoomType());


            }
            if(roomTypes.size()==0 || roomTypes.isEmpty()) break;
        }
        if(roomTypes.size()>0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ResponseData.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message("Fail! "+ roomTypes.toString() + "het phong")
                            .build()
            );
//        booking.setRooms(rooms.stream().map(room -> room.getId()).toList());
        booking.setUser(u);
        booking.setHotel(hotel);
        bookingDetailRepository.save(booking);
        roomRepository.saveAll(rooms);
        return ResponseEntity.ok(
                ResponseData.builder()
                        .status(HttpStatus.OK)
                        .message("Success")
                        .build()
        );
    }

    @Override
    public ResponseEntity<?> getAllBookedHotel() {
        UserDetails user=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ;
        List<BookingDetail> bookingDetails=bookingDetailRepository.findByUserEmail(user.getUsername());
        return ResponseEntity.ok(
                ResponseData.builder()
                        .status(HttpStatus.OK)
                        .message("Success")
                        .data(bookingDetails.stream()
                                .map(bookingDetail -> modelMapper.map(bookingDetail, BookingDto.class))
                                .peek(hotelDto -> {
                                    List<Review> reviewList=reviewRepository.findByHotelId(hotelDto.getId());
                                    double totalRate=0;
                                    for (Review review: reviewList
                                    ) {
                                        totalRate=totalRate+review.getRate();
                                    }
                                    double rate=totalRate/reviewList.size();
                                    hotelDto.setHotelRate(rate);
                                    hotelDto.setReviewQuantity(reviewList.size());
                                })
                                .toList())
                        .build()
        );
    }
}
