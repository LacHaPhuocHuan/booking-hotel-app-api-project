package com.laptrinhweb.service;

import com.laptrinhweb.dto.ResponseData;
import org.springframework.http.ResponseEntity;

public interface IHotelService {
    ResponseEntity<ResponseData> getAll();

    ResponseEntity<ResponseData> getById(Long id);

    ResponseEntity<?> findByKeyword(String keyword);

    ResponseEntity<?> getPopular();
}
