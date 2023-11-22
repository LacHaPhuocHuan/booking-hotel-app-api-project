package com.laptrinhweb.rest.restImpl;

import com.laptrinhweb.dto.ResponseData;
import com.laptrinhweb.rest.HotelRest;
import com.laptrinhweb.service.IHotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HotelRestImpl implements HotelRest
{
    private final IHotelService hotelService;
    @Override
    public ResponseEntity<ResponseData> getList() {
        try {
            return hotelService.getAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(500).body(ResponseData.builder().message("Server occur Problem").build());
    }

    @Override
    public ResponseEntity<ResponseData> getById(Long id) {
        try {
            return hotelService.getById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(500).body(ResponseData.builder().message("Server occur Problem").build());
    }

    @Override
    public ResponseEntity<?> search(String keyword) {
        try {
            return hotelService.findByKeyword(keyword);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(500).body(ResponseData.builder().message("Server occur Problem").build());
    }

    @Override
    public ResponseEntity<?> getPopular() {
        try {
            return hotelService.getPopular();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(500).body(ResponseData.builder().message("Server occur Problem").build());
    }
}
