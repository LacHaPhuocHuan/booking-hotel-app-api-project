package com.laptrinhweb.repository;

import com.laptrinhweb.entity.ImageDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDetailsReopository extends JpaRepository<ImageDetails, Long> {
}
