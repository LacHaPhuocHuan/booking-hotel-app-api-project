package com.laptrinhweb.dto;

import com.laptrinhweb.entity.Hotel;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;

public record ReviewDto(Long id,
                        float rate,
                        String content,
                        Long hotelId) {

}
