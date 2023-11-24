package com.laptrinhweb.dto;

import com.laptrinhweb.entity.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentMethodDto {
    private Long id;
    private String username;
    private String cardName;
    private String carNumber;
    private String numberCVV;
    private String memberSince;
    private PaymentMethod paymentMethod;

    @Override
    public String toString() {
        return "PaymentMethodDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", cardName='" + cardName + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", numberCVV='" + numberCVV + '\'' +
                ", memberSince='" + memberSince + '\'' +
                ", paymentMethod=" + paymentMethod +
                '}';
    }
}
