package com.laptrinhweb.rest.restImpl;

import com.laptrinhweb.dto.ResponseData;
import com.laptrinhweb.rest.FavouriteRest;
import com.laptrinhweb.service.IFavouriteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class FavouriteRestImpl implements FavouriteRest {
    private final IFavouriteService favouriteService;

    @Override
    public ResponseEntity<?> getAll() {
        try{
            return favouriteService.getAll();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ResponseEntity.status(500).body(ResponseData.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message("SERVER LOI ROI...(@ _ @)")
                .build());
    }

    @Override
    public ResponseEntity<?> favouriteHotel(Long hotelId) {
        try{
            return favouriteService.favouriteHotel(hotelId);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ResponseEntity.status(500).body(ResponseData.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("SERVER LOI ROI...(@ _ @)")
                .build());
    }

    @Override
    public ResponseEntity<?> unfavouriteHotel(Long hotelId) {
        try{
            return favouriteService.unfavouriteHotel(hotelId);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ResponseEntity.status(500).body(ResponseData.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("SERVER LOI ROI...(@ _ @)")
                .build());
    }
}
