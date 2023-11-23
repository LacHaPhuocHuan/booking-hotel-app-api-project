package com.laptrinhweb.rest;

import com.laptrinhweb.dto.ResponseData;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/hotel")
//@Api(value = "Hotel API", tags = "Provide Hotel Information")

public interface HotelRest {
    @GetMapping("")
//    @ApiOperation(value = "Get List of Hotels", notes = "Retrieve a list of hotels.")
    public ResponseEntity<ResponseData> getList();

    @GetMapping("/{id}")
//    @ApiOperation(value = "Get Hotel by ID", notes = "Retrieve a hotel by its ID.")
    public ResponseEntity<ResponseData> getById(@PathVariable("id") Long id);

    @GetMapping("/search")
//    @ApiOperation(value = "Search Hotels", notes = "Search hotels by keyword.")

    public ResponseEntity<?> search(@RequestParam("keyword") String keyword);

    @GetMapping("/popular")
//    @ApiOperation(value = "Get Popular Hotels", notes = "Retrieve a list of popular hotels.")
    public ResponseEntity<?> getPopular();


}
