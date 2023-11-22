package com.laptrinhweb.rest;

import com.laptrinhweb.dto.ResponseData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/hotel")
public interface HotelRest {
    @GetMapping("")
    public ResponseEntity<ResponseData> getList();

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> getById(@PathVariable("id") Long id);

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam("keyword") String keyword);

    @GetMapping("/popular")
    public ResponseEntity<?> getPopular();


}
