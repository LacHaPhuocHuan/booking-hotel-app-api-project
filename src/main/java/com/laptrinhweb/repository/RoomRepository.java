package com.laptrinhweb.repository;

import com.laptrinhweb.entity.Room;
import com.laptrinhweb.entity.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByRoomStatus(RoomStatus roomStatus);

    List<Room> findByHotelId(Long hotelId);
}
