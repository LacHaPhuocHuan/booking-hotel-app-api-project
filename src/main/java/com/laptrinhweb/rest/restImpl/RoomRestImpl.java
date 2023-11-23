package com.laptrinhweb.rest.restImpl;

import com.laptrinhweb.rest.RoomRest;
import com.laptrinhweb.service.IRoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RoomRestImpl implements RoomRest {
    private final IRoomService roomService;
    @Override
    public ResponseEntity<?> getById(Long id) {
        return ResponseEntity.ok(roomService.fingById(id));
    }
}
