package com.laptrinhweb.service.serviceImpl;

import com.laptrinhweb.repository.RoomRepository;
import com.laptrinhweb.service.IRoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements IRoomService
{
    private final RoomRepository roomRepository;
    @Override
    public Object fingById(Long id) {
        try {
            return roomRepository.findById(id).orElseThrow();
        }catch (Exception ex){
            return null;

        }
    }
}
