package com.laptrinhweb.repository;

import com.laptrinhweb.entity.Farvourite;
import com.laptrinhweb.entity.FarvouriteKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarvouriteRepository extends JpaRepository<Farvourite, FarvouriteKey
        > {
}
