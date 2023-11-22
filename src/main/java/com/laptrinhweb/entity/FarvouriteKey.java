package com.laptrinhweb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FarvouriteKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "hotel_id")
    private Long hotelId;
}
