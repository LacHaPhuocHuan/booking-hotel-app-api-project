package com.laptrinhweb.dto;

import com.laptrinhweb.entity.ImageDetails;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelDto {
    private Long id;
    private String name;
    private String address;
    private double price;
    private String overview;
    private List<ImageDto> imageDetails;
    private Integer reviewQuantity;

}
