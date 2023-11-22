package com.laptrinhweb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbFarvourite")
public class Farvourite {
    @EmbeddedId
    private FarvouriteKey key;
    private Date dateAt;
    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("hotelId")
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

}
