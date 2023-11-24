package com.laptrinhweb.repository;

import com.laptrinhweb.entity.Farvourite;
import com.laptrinhweb.entity.FarvouriteKey;
import com.laptrinhweb.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FarvouriteRepository extends JpaRepository<Farvourite, FarvouriteKey
        > {
    List<Farvourite> findHotelByUserEmailAndIsFavourited(String username, boolean b);
}
