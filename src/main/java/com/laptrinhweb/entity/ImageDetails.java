package com.laptrinhweb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

@Entity
@Table(name = "tbImageDetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String img;
    @Enumerated(EnumType.STRING)
    private ImgType imgType;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

}
