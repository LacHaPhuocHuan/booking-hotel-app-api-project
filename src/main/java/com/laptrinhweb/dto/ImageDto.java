package com.laptrinhweb.dto;

import com.laptrinhweb.entity.ImgType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {
    private Long id;
    private String img;
    private ImgType imgType;

}
