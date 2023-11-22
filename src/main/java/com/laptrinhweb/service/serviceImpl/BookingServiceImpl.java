package com.laptrinhweb.service.serviceImpl;

import com.laptrinhweb.dto.BookingDto;
import com.laptrinhweb.dto.HotelDto;
import com.laptrinhweb.dto.ResponseData;
import com.laptrinhweb.entity.BookingDetail;
import com.laptrinhweb.repository.BookingDetailRepository;
import com.laptrinhweb.service.IBookingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements IBookingService{
    private final BookingDetailRepository bookingDetailRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> bookHotel(Long hotelId, BookingDto bookingDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllBookedHotel() {
        UserDetails user=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ;
        List<BookingDetail> bookingDetails=bookingDetailRepository.findByUserEmail(user.getUsername());
        return ResponseEntity.ok(
                ResponseData.builder()
                        .status(HttpStatus.OK)
                        .message("Success")
                        .data(bookingDetails.stream().map(bookingDetail -> modelMapper.map(bookingDetail, BookingDto.class)).toList())
                        .build()
        );
    }
}
