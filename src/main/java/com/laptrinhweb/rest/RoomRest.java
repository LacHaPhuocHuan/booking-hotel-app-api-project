package com.laptrinhweb.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/room")
public interface RoomRest {
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id);
}
