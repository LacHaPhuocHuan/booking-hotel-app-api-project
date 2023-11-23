package com.laptrinhweb.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/room")
//@Api(value = "Hotel API", tags = "Room On Hotel")

public interface RoomRest {
    @GetMapping("/{id}")
//    @ApiOperation(value = "Get Room by ID", notes = "Retrieve information about a room by its ID.")
    public ResponseEntity<?> getById(@PathVariable("id") Long id);
}
