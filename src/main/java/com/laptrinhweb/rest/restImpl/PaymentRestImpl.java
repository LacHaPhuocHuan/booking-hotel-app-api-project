package com.laptrinhweb.rest.restImpl;

import com.laptrinhweb.dto.PaymentMethodDto;
import com.laptrinhweb.dto.ResponseData;
import com.laptrinhweb.repository.PaymentAccountRepository;
import com.laptrinhweb.rest.PaymentRest;
import com.laptrinhweb.service.IPaymentServive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PaymentRestImpl implements PaymentRest {
    private final IPaymentServive paymentServive;
    @Override
    public ResponseEntity<?> getPaymentMethod() {
        try{
            return paymentServive.getAll();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ResponseEntity.status(500).body(ResponseData.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("SERVER LOI ROI...(@ _ @)")
                .build());
    }

    @Override
    public ResponseEntity<?> createPaymentMethod(PaymentMethodDto dto) {
        try{
            return paymentServive.createPaymentMethod(dto) ;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ResponseEntity.status(500).body(ResponseData.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("SERVER LOI ROI...(@ _ @)")
                .build());
    }

    @Override
    public ResponseEntity<?> deletePaymentAccount(Long paymentId) {
        try{
            return paymentServive.deletePayment(paymentId) ;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ResponseEntity.status(500).body(ResponseData.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("SERVER LOI ROI...(@ _ @)")
                .build());
    }
}
