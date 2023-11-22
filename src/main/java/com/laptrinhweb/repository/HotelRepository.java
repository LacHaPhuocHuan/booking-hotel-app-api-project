package com.laptrinhweb.repository;

import com.laptrinhweb.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByNameContainsOrAddressContainsOrOverviewContainsOrPriceEquals(String name, String address, String overview, Double price);

    @Query(
            "Select h, Count(r) as reviewCount " +
                    "From Hotel h LEFT JOIN Review r On h.id = r.hotel.id " +
                    "Group by h.id " +
                    "Order by reviewCount DESC"
    )
    List<Object[]> findHotelsWithMostReviews();
}
