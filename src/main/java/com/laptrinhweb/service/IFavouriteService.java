package com.laptrinhweb.service;

import org.springframework.http.ResponseEntity;

public interface IFavouriteService {
    ResponseEntity<?> getAll();

    ResponseEntity<?> favouriteHotel(Long hotelId);

    ResponseEntity<?> unfavouriteHotel(Long hotelId);
}
