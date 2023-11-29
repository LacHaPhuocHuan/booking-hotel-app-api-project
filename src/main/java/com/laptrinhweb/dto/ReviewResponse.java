package com.laptrinhweb.dto;

import com.laptrinhweb.entity.Hotel;
import com.laptrinhweb.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {
    private Long id;
    private float rate;
    private String content;
    private String username;
    private byte[] avatarImg;


}
