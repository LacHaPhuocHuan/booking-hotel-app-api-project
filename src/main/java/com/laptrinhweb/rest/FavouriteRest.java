package com.laptrinhweb.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/favourite-hotel")
public interface FavouriteRest {
    @GetMapping("")
    public ResponseEntity<?> getAll();
    @PostMapping("/{hotelId}")
    public ResponseEntity<?> favouriteHotel(@PathVariable("hotelId") Long hotelId);

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<?> unfavouriteHotel(@PathVariable("hotelId") Long hotelId);

}
